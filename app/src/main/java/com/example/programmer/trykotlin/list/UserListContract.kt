package com.example.programmer.trykotlin.list

import android.support.annotation.StringRes
import com.example.programmer.trykotlin.BasePresenter
import com.example.programmer.trykotlin.BaseView
import com.example.programmer.trykotlin.R
import com.example.programmer.trykotlin.model.UserModel

interface UserListContract {
    interface View: BaseView {
        fun stopRefreshing()
        fun addNewUsers(newUserList: List<UserModel>)
        fun updateScrollListener()
        fun setEmpty(@StringRes textIfEmpty: Int = R.string.no_data_available)
        fun clean()
        fun stopSwipy()
    }

    interface Presenter: BasePresenter {
        fun printUsers()
        fun start()
        fun requestNewUsers()
        fun search(query: String = "")
        fun searchNew()
        fun resetPage()
    }
}