package com.example.programmer.trykotlin.list

import com.example.programmer.trykotlin.BasePresenter
import com.example.programmer.trykotlin.BaseView
import com.example.programmer.trykotlin.model.UserModel

interface UserListContract {
    interface View: BaseView {
        fun showListUsers(listUserModel: List<UserModel>)
        fun stopRefreshing()
    }

    interface Presenter: BasePresenter {
        fun printUsers()
    }
}