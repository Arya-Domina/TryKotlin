package com.example.programmer.trykotlin.list

import com.example.programmer.trykotlin.BasePresenter
import com.example.programmer.trykotlin.BaseView
import com.example.programmer.trykotlin.model.RepoUserModel
import com.example.programmer.trykotlin.model.UserModel

interface UserListContract {
    interface View: BaseView {

        fun showListUsers(listUserModel: List<UserModel>)

        fun updateList()

        fun showOneUser(userModel: UserModel)

    }

    interface Presenter: BasePresenter {

        fun request()

        fun getList(): List<UserModel>

        fun update()

        fun getRepo(): RepoUserModel
        fun getUsver()
        fun printUsers()
        fun getAllUsersDetails()
    }
}