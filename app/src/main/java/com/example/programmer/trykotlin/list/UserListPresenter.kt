package com.example.programmer.trykotlin.list

import com.example.programmer.trykotlin.model.RepoUserModel

class UserListPresenter(private val view: UserListContract.View) : UserListContract.Presenter {

    private var currentQuery: String? = null

    override fun printUsers() {
        RepoUserModel.instance.printString()
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCurrentQueryForLog() = currentQuery?.let { it } ?: ""

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

    override fun searchUsers(query: String?, page: Int, perPage: Int) {

        query?.let { currentQuery = it }
        currentQuery?.let {
            RepoUserModel.instance.searchPage(it, page, perPage)
                    .subscribe({
                        view.showListUsers(it.items, "Никого не найдено по такому запросу")
                        view.turnOnSearchMode()
                        view.setButtons(page, it.totalCount % perPage + 1)
                    }, {
                        println("presenter searchPage onError")
                    })
        }
    }

}
