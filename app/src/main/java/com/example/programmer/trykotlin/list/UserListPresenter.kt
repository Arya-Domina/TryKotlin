package com.example.programmer.trykotlin.list

import com.example.programmer.trykotlin.model.RepoUserModel

class UserListPresenter(private val view: UserListContract.View) : UserListContract.Presenter {

    override fun printUsers() {
        RepoUserModel.instance.printString()
    }

    override fun start() {
        RepoUserModel.instance.getAllUsers()
                .subscribe({
                    printUsers()
                    view.showListUsers(it)
                }, {
                    view.stopRefreshing()
                })
    }

}
