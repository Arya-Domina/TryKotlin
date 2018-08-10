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

    fun getList() = userList

    private var searchList = SearchResultModel()

    private fun zip(old: List<UserModel>, new: List<UserModel>): List<UserModel> { //without replace edited users
        val res = mutableListOf<UserModel>()
        res.addAll(old)
        new.filter { !res.contains(it) }.forEach({res.add(it)})
        return res.toList()
    }

    fun printString() = userList.forEach { println(it.toString()) }
    private fun printString(list: List<UserModel>) = list.forEach { println(it.toString()) }

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
            App.getApi().userDetails(login)
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

    fun searchNewUsers(query: String, page: Int): Observable<SearchResultModel> =
            App.getApi().searchUserPagination(query, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext({
                        println("searchNewUsers doOnNext, query: $query, page: $page")
                        val l = searchList.items.toMutableList()
                        it.items.forEach({ l.add(it) })
                        searchList.items = l.toList()
                    })
                    .doOnError({
                        println("searchNewUsers doOnError, query: $query, page: $page")
                        ErrorHandlerHelper.showSnake(it)
                    })

    fun getNewUsers(): Observable<List<UserModel>> {
        return App.getApi().getUsersSince(if (!userList.isEmpty()) userList.last().id ?: 0 else 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext({
                    println("getNewUsers")
                    printString(it)
                    userList = zip(userList, it)
                })
                .doOnError({ErrorHandlerHelper.showSnake(it)})
    }

}

