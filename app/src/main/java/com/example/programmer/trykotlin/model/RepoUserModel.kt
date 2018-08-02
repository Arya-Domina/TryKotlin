package com.example.programmer.trykotlin.model

import com.example.programmer.trykotlin.App
import com.example.programmer.trykotlin.util.ErrorHandlerHelper
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RepoUserModel {

    private object Holder {
        val INSTANCE = RepoUserModel()
    }

    companion object {
        val instance: RepoUserModel by lazy { Holder.INSTANCE }
    }

    private var userList = listOf<UserModel>() //cache

    fun printString() = userList.forEach { println(it.toString()) }

    private fun getUserByUsername(username: String): UserModel? { //from cache
        return userList.find { it.login == username }
    }

    private fun saveUser(userModel: UserModel) { //in cache
        userList.find { it.id == userModel.id }?.let {
            it.name = userModel.name
            it.email = userModel.email
            it.location = userModel.location
            it.company = userModel.company
            it.repositoriesCount = userModel.repositoriesCount
            it.hasDetails = true
        }
                ?: println("saveUser not found")
    }

    private fun requestUserDetails(login: String): Observable<UserModel> =
            App.getApi().userDetailsOb(login)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext({
                        println("doOnNext requestUserDetails")
                        it.hasDetails = true //
                        saveUser(it)
                    })
                    .doOnError({
                        println("doOnError requestUserDetails")
                        ErrorHandlerHelper.showSnake(it)
                    })


    fun getUserDetails(username: String): Observable<UserModel> {
        val user: UserModel? = getUserByUsername(username)

        return if (user == null) {
            println("getUserDetails true")
            requestUserDetails(username)
                    .doOnNext({
                        println("doOnNext getUserDetails user == null")
                    })
        } else {
            println("getUserDetails false")
            Observable.just(user)
                    .mergeWith(requestUserDetails(username))
                    .doOnNext({
                        println("doOnNext getUserDetails user != null")
                    })
        }
    }

    private fun requestAllUsers(): Observable<List<UserModel>> =
            App.getApi().getUsersOb()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext({
                        println("requestAllUsers doOnNext")
                        userList = it
                    })
                    .doOnError({
                        println("requestAllUsers doOnError")
                        ErrorHandlerHelper.showSnake(it)
                    })

    fun getAllUsers(): Observable<List<UserModel>> {
        return if (userList.isEmpty()) {
            println("getAllUsers list is empty")
            requestAllUsers()
        } else {
            println("getAllUsers list is not empty")
            Observable.just(userList)
                    .mergeWith(requestAllUsers())
        }
    }

    fun searchPage(query: String, page: Int, perPage: Int): Observable<SearchResultModel> =
            App.getApi().searchUserPagination(query, page, perPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext({
                        println("searchPage doOnNext, query: $query, page: $page, perPage: $perPage")
                    })
                    .doOnError({
                        println("searchPage doOnError, query: $query, page: $page, perPage: $perPage")
                        ErrorHandlerHelper.showSnake(it)
                    })

}

