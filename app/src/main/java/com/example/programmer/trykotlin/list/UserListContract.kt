package com.example.programmer.trykotlin.list

import android.support.annotation.StringRes
import com.example.programmer.trykotlin.BasePresenter
import com.example.programmer.trykotlin.BaseView
import com.example.programmer.trykotlin.R
import com.example.programmer.trykotlin.model.UserModel

interface UserListContract {
    interface View: BaseView {
        fun stopRefreshing()
        fun showListUsers(listUserModel: List<UserModel>, textIfEmpty: String)
        fun showListUsers(listUserModel: List<UserModel>, @StringRes textIfEmpty: Int = R.string.no_data_available)
        fun setButtons(page: Int, lastPage: Int)
        fun turnOnSearchMode()
        fun turnOffSearchMode()
    }

    interface Presenter: BasePresenter {
        fun printUsers()
        fun requestUsers()
        fun searchUsers(query: String? = null, page: Int = 1, perPage: Int = 30)
        fun getCurrentQueryForLog(): String
    }
}