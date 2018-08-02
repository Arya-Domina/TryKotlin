package com.example.programmer.trykotlin.list

import com.example.programmer.trykotlin.model.RepoUserModel
import com.example.programmer.trykotlin.model.UserModel

class UserListPresenter(private val view: UserListContract.View) : UserListContract.Presenter {

    private var currentQuery: String = ""

    override fun printUsers() {
        RepoUserModel.instance.printString()
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCurrentQueryForLog() = currentQuery

    override fun requestUsers() {
        RepoUserModel.instance.getAllUsers()
                .subscribe({
                    printUsers()
                    view.showListUsers(it)
                    view.turnOffSearchMode()
                }, {
                    view.stopRefreshing()
                })
    }

    override fun searchUsersByLastQuery(page: Int, perPage: Int) {
        if (currentQuery != "") {
            RepoUserModel.instance.searchPage(currentQuery, page, perPage)
                    .subscribe({
                        println("presenter searchUsersByLastQuery searchPage onNext")
                        println("totalCount ${it.totalCount}")

                        bindView(it.items)
                    }, {
                        println("presenter searchPage onError")
                    })
        }
    }

    override fun searchUsers(query: String, page: Int, perPage: Int) {
        if (query != "") {
            currentQuery = query

            RepoUserModel.instance.searchPage(query, page, perPage)
                    .subscribe({
                        println("presenter searchUsers searchPage onNext")
                        println("totalCount ${it.totalCount}")

                        setView(page, it.totalCount % perPage + 1)
                        bindView(it.items)
                    }, {
                        println("presenter searchPage onError")
                    })
        }
    }

    private fun bindView(items: List<UserModel>) {
        view.showListUsers(items, "Никого не найдено по такому запросу")
        view.bindButtonsClickable()

    }

    private fun setView(page: Int, count: Int) {
        view.setPage(page, count)
        view.bindButtonsOnClick()
        view.turnOnSearchMode()
    }

}
