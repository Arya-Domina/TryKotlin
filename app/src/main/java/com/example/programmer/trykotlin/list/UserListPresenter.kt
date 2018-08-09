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

    override fun getNewUsers() {
        RepoUserModel.instance.getNewUsers()
                .subscribe({
                    view.addNewUsers(it)
                }, {
                    view.stopRefreshing()
                })
    }

    override fun search(query: String, page: Int, per_page: Int) {
        RepoUserModel.instance.searchPage(query, page, per_page).subscribe({
            println("presenter searchPage onNext")
            println("totalCount ${it.totalCount}")
            view.showListUsers(it.items, "Никого не найдено по такому запросу")
        }, {
            println("presenter searchPage onError")
        })
    }

}
