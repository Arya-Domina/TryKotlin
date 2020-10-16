package com.example.programmer.trykotlin.details

import com.example.programmer.trykotlin.BasePresenter
import com.example.programmer.trykotlin.BaseView
import com.example.programmer.trykotlin.model.RepoModel
import com.example.programmer.trykotlin.model.UserModel

interface UserDetailsContract {
    interface View : BaseView {
        fun bindUserInfo(user: UserModel)
        fun bindUserRepo(repoList: List<RepoModel>)
        fun bindUser(user: UserModel)
    }

    interface Presenter : BasePresenter {
        fun getUserInfo(username: String)
        fun getUserRepos(username: String)
        fun getUser(username: String)
    }

}