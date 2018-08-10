package com.example.programmer.trykotlin.list

import com.example.programmer.trykotlin.R
import com.example.programmer.trykotlin.model.RepoUserModel
import com.example.programmer.trykotlin.model.UserModel

class UserListPresenter(private val view: UserListContract.View) : UserListContract.Presenter {

    private var queryString: String = ""
    private var page = 1

    override fun resetPage() {
        page = 1
    }

    override fun printUsers() {
        RepoUserModel.instance.printString()
    }

    override fun getCurrentUsers(): List<UserModel> =
            RepoUserModel.instance.getList()

    override fun requestNewUsers() {
        RepoUserModel.instance.getNewUsers()
                .subscribe({
                    view.addNewUsers(it)
                }, {
                    view.stopRefreshing()
                })
    }

    override fun search(query: String) {
        if (query != "") {
            queryString = query
            view.clean()
            page = 1
        } else page++
        RepoUserModel.instance.searchNewUsers(queryString, page)
                .subscribe({
                    if (it.totalCount == 0)
                        view.setEmpty(R.string.not_found)
                    else
                        view.addNewUsers(it.items)
                }, {})
    }

}
