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

//    private fun addUsers(newUsers: List<UserModel>) {
//        println("addUsers old list")
//        printString()
//        userList.toMutableList().addAll(newUsers)
//        println("addUsers new list")
//        printString()
//    }

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

    fun getAllUsers(): Observable<List<UserModel>> =
            Observable.just(userList)
                    .mergeWith(App.getApi().getUsersOb())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext({
                        println("getAllUsers doOnNext")
//                        userList = it
                        userList = zip(userList, it)
                        //addUsers
                    })
                    .doOnError({
                        println("getAllUsers doOnError")
                        ErrorHandlerHelper.showSnake(it)
                    })

    fun searchPage(query: String, page: Int, per_page: Int): Observable<SearchResultModel> =
            App.getApi().searchUserPagination(query, page, per_page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext({
                        println("searchPage doOnNext, query: $query, page: $page, per_page: $per_page")
                    })
                    .doOnError({
                        println("searchPage doOnError, query: $query, page: $page, per_page: $per_page")
                        ErrorHandlerHelper.showSnake(it)
                    })

    fun getNewUsers(): Observable<List<UserModel>> {
        return App.getApi().getUsersSince(userList.last().id ?: 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext({
                    println("getNewUsers")
                    printString(it)
                    userList = zip(userList, it)
//                    addUsers(it)
                })
                .doOnError({ErrorHandlerHelper.showSnake(it)})
    }

}

