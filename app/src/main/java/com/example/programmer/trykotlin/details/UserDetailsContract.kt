package com.example.programmer.trykotlin.details

import com.example.programmer.trykotlin.BasePresenter
import com.example.programmer.trykotlin.BaseView
import com.example.programmer.trykotlin.model.UserModel

interface UserDetailsContract {
    interface View : BaseView {
        fun bindUsver(user: UserModel)
    }

    interface Presenter : BasePresenter {
        fun getUsver(username: String)
    }

}