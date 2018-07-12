package com.example.programmer.trykotlin

import com.example.programmer.trykotlin.model.UserModel

class MainContract {
    interface View: BaseView<Presenter> {

        fun showListUsers(listUserModel: List<UserModel>)

        fun updateList()

        fun showOneUser(userModel: UserModel)

    }

    interface Presenter: BasePresenter {

        fun request()

        fun getList(): List<UserModel>

        fun getUser(id: Int): UserModel?

        fun update()

    }
}