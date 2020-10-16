package com.example.programmer.trykotlin.details

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import com.example.programmer.trykotlin.R
import com.example.programmer.trykotlin.model.RepoModel

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val layout by lazy {
        itemView.findViewById<LinearLayout>(R.id.repo_list_layout)
    }

    fun bind(repo: RepoModel) {
        val repoName = PairTextView(layout.context, R.string.fullName, repo.name ?: "")
        repoName.setGravityCenter()
        layout.addView(repoName)
        layout.add(R.string.description, repo.description)
        layout.add(R.string.language, repo.language)
        layout.add(R.string.created_at, repo.createdAt)
        layout.add(R.string.stargazers_count, repo.stargazersCount)
    }

    private fun LinearLayout.add(resId: Int, value: String?) {
        value?.let { this.addView(PairTextView(this.context, resId, it)) }
    }

    private fun LinearLayout.add(resId: Int, value: Int?) {
        this.add(resId, value.toString())
    }
}