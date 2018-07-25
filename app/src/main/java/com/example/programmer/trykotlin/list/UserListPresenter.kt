package com.example.programmer.trykotlin.list

import com.example.programmer.trykotlin.model.RepoUserModel
import com.example.programmer.trykotlin.model.UserModel

class UserListPresenter(private val view: UserListContract.View) : UserListContract.Presenter {

    override fun printUsers() {
        RepoUserModel.instance.printString()
    }

    override fun request() {
    }

    override fun start() {
        RepoUserModel.instance.getAllUsers()
                .subscribe({
                    view.showListUsers(it)
                }, {})
    }

    override fun getList(): List<UserModel> =
            RepoUserModel.instance.getUserList()

    override fun getRepo(): RepoUserModel =
            RepoUserModel.instance

    override fun update() =
            view.updateList()

}
