package com.example.programmer.trykotlin.details

import com.example.programmer.trykotlin.model.RepoUserModel

class UserDetailsPresenter(private val view: UserDetailsContract.View) : UserDetailsContract.Presenter {
    init {
        println("init UserDetailsPresenter")
    }

    override fun start() {
    }

    override fun getUsver(username: String) {
        RepoUserModel.instance.getUserDetailsOb(username).subscribe({
            view.bindUsver(it)
            RepoUserModel.instance.saveUserById(it)
        })
    }
}