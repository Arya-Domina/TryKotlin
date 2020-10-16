package com.example.programmer.trykotlin.list

import com.example.programmer.trykotlin.Constants.Companion.MAX_RESULT_COUNT
import com.example.programmer.trykotlin.R
import com.example.programmer.trykotlin.model.RepoUserModel

class UserListPresenter(private val view: UserListContract.View) : UserListContract.Presenter {

    private var currentQuery: String? = null
    private var queryString: String = ""
    private var page = 1

    override fun resetPage() {
        page = 1
    }

    override fun printUsers() {
        RepoUserModel.instance.printString()
    }

    override fun start() {
        if (RepoUserModel.instance.getList().isEmpty()) {
            view.setEmpty(R.string.no_data_available)
            requestNewUsers()
        } else {
            view.addNewUsers(RepoUserModel.instance.getList())
            if (RepoUserModel.instance.getUsersCount() != MAX_RESULT_COUNT) {
                view.updateScrollListener()
            } else {
                view.stopSwipy()
            }
        }
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

    override fun requestNewUsers() {
        RepoUserModel.instance.getNewUsers()
                .subscribe({
                    println("presenter requestNewUsers Action")
                    view.stopRefreshing()
                    view.addNewUsers(it)
                    if (RepoUserModel.instance.getUsersCount() != MAX_RESULT_COUNT) {
                        view.updateScrollListener()
                    } else {
                        view.stopSwipy()
                    }
                }, {
                    println("presenter requestNewUsers OnError")
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

    override fun search(query: String) {
        if (query != "") {
            view.clean()
            RepoUserModel.instance.resetSearchResult()
            queryString = query
            page = 1
            searchNew()
        }
    }

    override fun searchNew() {
        RepoUserModel.instance.searchNewUsers(queryString, page++)
                .subscribe({
                    if (it.totalCount == 0)
                        view.setEmpty(R.string.not_found)
                    else {
                        view.stopRefreshing()
                        view.addNewUsers(it.items)
                        println("count: ${RepoUserModel.instance.getSearchCount()}, total: ${it.totalCount}")
                        if (RepoUserModel.instance.getSearchCount() != MAX_RESULT_COUNT && RepoUserModel.instance.getSearchCount() != it.totalCount) {
                            view.updateScrollListener()
                        } else {
                            view.stopSwipy()
                        }
                    }
                }, {})
    }

}
