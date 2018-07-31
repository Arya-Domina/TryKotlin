package com.example.programmer.trykotlin.list

import android.support.annotation.StringRes
import com.example.programmer.trykotlin.BasePresenter
import com.example.programmer.trykotlin.BaseView
import com.example.programmer.trykotlin.model.UserModel

interface UserListContract {
    interface View: BaseView {
        fun showListUsers(listUserModel: List<UserModel>)
        fun stopRefreshing()
        fun showListUsers(listUserModel: List<UserModel>, textIfEmpty: String)
        fun showListUsers(listUserModel: List<UserModel>, @StringRes textIfEmpty: Int)
    }

    interface Presenter: BasePresenter {
        fun printUsers()
    }
}