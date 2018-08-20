package com.example.programmer.trykotlin.details

import com.example.programmer.trykotlin.model.RepoUserModel

class UserDetailsPresenter(private val view: UserDetailsContract.View) : UserDetailsContract.Presenter {
    init {
        println("init UserDetailsPresenter")
    }

    override fun getUser(username: String) {
        RepoUserModel.instance.getUserDetails(username).subscribe({
            view.bindUser(it)
        }, {})
    }

    override fun getUserInfo(username: String) {
        RepoUserModel.instance.getUserDetails(username).subscribe({
            view.bindUserInfo(it)
        }, {})
    }

    override fun getUserRepos(username: String) {
        RepoUserModel.instance.getUserRepos(username).subscribe({
            view.bindUserRepo(it)
        }, {})
    }
}