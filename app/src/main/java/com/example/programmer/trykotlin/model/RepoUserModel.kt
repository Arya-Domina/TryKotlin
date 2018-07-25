package com.example.programmer.trykotlin.model

import com.example.programmer.trykotlin.App
import com.example.programmer.trykotlin.util.ErrorHandlerHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    fun getUserList() = userList

    private fun getUserByUsername(username: String): UserModel? { //from cache
        return userList.find { it.login == username }
    }

    private fun saveUserById(userModel: UserModel) { //in cache
        userList.find { it.id == userModel.id }?.let {
            it.name = userModel.name
            it.email = userModel.email
            it.company = userModel.company
            it.repositoriesCount = userModel.repositoriesCount
            it.hasDetails = true
        }
                ?: println("saveUserById not found")
    }

    fun printString() = userList.forEach { println(it.toString())}

    private fun requestUserDetails(login: String): Observable<UserModel> =
            App.getApi().userDetailsOb(login)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext({
                        println("doOnNext requestUserDetails")
                        saveUserById(it)
                        it.hasDetails = true
                    })
                    .doOnError({
                        println("doOnError requestUserDetails")
                        ErrorHandlerHelper.showSnake(it)
                    })


    fun getUserDetails(username: String): Observable<UserModel> {
        val user: UserModel? = getUserByUsername(username) // take from cache

        if (user == null/* || !user.hasDetails*/) {
            println("getUserDetails true")
            return requestUserDetails(username)
                    .doOnNext({
                        //
                        println("doOnNext getUserDetails user == null")
                    })
        } else {
            println("getUserDetails false")
            return Observable.just(user)
                    .mergeWith(requestUserDetails(username))
                    .doOnNext({
                        //
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

    fun requestSearch(query: String) =
            App.getApi().searchUser(query).enqueue(object:Callback<SearchResultModel>{
                override fun onFailure(call: Call<SearchResultModel>?, t: Throwable?) {
                    println("searchUser onFailure")
                    println(t)
                }

                override fun onResponse(call: Call<SearchResultModel>?, response: Response<SearchResultModel>?) {
                    println("searchUser onResponse")

                    response?.body()?.items?.let { it1 -> userList = it1}
                }
            })

}

