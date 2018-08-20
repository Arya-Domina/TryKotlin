package com.example.programmer.trykotlin.details

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.programmer.trykotlin.Constants.Companion.USER
import com.example.programmer.trykotlin.R
import com.example.programmer.trykotlin.model.RepoModel
import com.example.programmer.trykotlin.model.UserModel
import com.jakewharton.rxbinding2.view.RxView
import com.squareup.picasso.Picasso

class UserDetailsActivity : AppCompatActivity(), UserDetailsContract.View {

    private val presenter: UserDetailsPresenter by lazy {
        UserDetailsPresenter(this)
    }
    private val infoList by lazy {
        findViewById<LinearLayout>(R.id.info_list)
    }
    private val repoList by lazy {
        findViewById<LinearLayout>(R.id.repo_list)
    }
    private val imageView by lazy {
        findViewById<ImageView>(R.id.image)
    }
    private val buttonRepo: Button by lazy {
        findViewById<Button>(R.id.repo_button)
    }
    private val buttonInfo: Button by lazy {
        findViewById<Button>(R.id.info_button)
    }
    private var isShownInfo = true

    override fun getView(): View {
        return infoList
    }

    private fun add(resId: Int, value: String?) {
        value?.let { infoList.addView(PairTextView(this, resId, it)) }
    }

    private fun add(resId: Int, value: Int?) {
        value?.let { infoList.addView(PairTextView(this, resId, it.toString())) }
    }

    private fun add(resId: Int, value: Boolean?) {
        value?.let { infoList.addView(PairTextView(this, resId, if (it) "yes" else "no")) }
    }

    override fun bindUser(user: UserModel){
        println("bindUser $user")
        infoList.removeAllViews()
        Picasso.get().load(user.avatarUrl).fit().placeholder(R.drawable.icon_placeholder).error(R.drawable.icon_error).into(imageView)
        add(R.string.login, user.login)
    }

    override fun bindUserInfo(user: UserModel) {
        println("bindUserInfo $user")
        infoList.removeAllViews()

        add(R.string.login, user.login)
        if (user.hasDetails) {
            add(R.string.name, user.name)
            add(R.string.location, user.location)
            add(R.string.company, user.company)
            add(R.string.email, user.email)
            add(R.string.repos, user.repositoriesCount)
        } else {
            add(R.string.not_user, "details")
        }
    }

    override fun bindUserRepo(repoList: List<RepoModel>) {
        println("bindUserRepo size: ${repoList.size}")

        infoList.removeAllViews()
        add(R.string.id, repoList[0].id)
        add(R.string.fullName, repoList[0].fullName)
        add(R.string.owner, repoList[0].user?.login)
        add(R.string.is_private, repoList[0].private)
        add(R.string.description, repoList[0].description)
        add(R.string.created_at, repoList[0].createdAt)
        add(R.string.language, repoList[0].language)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_details)
        println("UserDetailsActivity onCreate")
        start()

        val username = intent.getSerializableExtra(USER) as String?
        println("UserDetailsActivity onCreate username $username")
        username?.let { presenter.getUser(it) } ?: println("no login")

//        buttonInfo.setOnClickListener { username?.let { presenter.getUserInfo(it) } ?: println("no login") }
//        buttonRepo.setOnClickListener { username?.let { presenter.getUserRepos(it) } ?: println("no login") }

        RxView.clicks(buttonInfo)/*.debounce(500, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())*/
                .subscribe({
                    username?.let { presenter.getUserInfo(it) } ?: println("no login")
        })


    }
}