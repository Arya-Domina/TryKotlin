package com.example.programmer.trykotlin

import com.example.programmer.trykotlin.model.RepoUserModel
import com.example.programmer.trykotlin.model.UserModel

interface MainContract {
    interface View: BaseView {

        fun showListUsers(listUserModel: List<UserModel>)

        fun updateList()

        fun showOneUser(userModel: UserModel)

    }

    interface Presenter: BasePresenter {

        fun request()

        fun getList(): List<UserModel>

        fun getUser(id: Int): UserModel?

        fun update()

        fun getRepo(): RepoUserModel
    }
}