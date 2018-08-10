package com.example.programmer.trykotlin.list

import android.support.annotation.StringRes
import com.example.programmer.trykotlin.BasePresenter
import com.example.programmer.trykotlin.BaseView
import com.example.programmer.trykotlin.R
import com.example.programmer.trykotlin.model.UserModel

interface UserListContract {
    interface View: BaseView {
        fun stopRefreshing()
        fun showListUsersSearch(listUserModel: List<UserModel>, @StringRes textIfEmpty: Int = R.string.no_data_available)
        fun addNewUsers(newUserList: List<UserModel>)
        fun setEmpty(textIfEmpty: Int)
        fun clean()
    }

    interface Presenter: BasePresenter {
        fun printUsers()
        fun requestNewUsers()
        fun search(query: String = "")
        fun resetPage()
        fun getCurrentUsers(): List<UserModel>
    }
}