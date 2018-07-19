package com.example.programmer.trykotlin.model

import com.example.programmer.trykotlin.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RepoUserModel {

    private object Holder {
        val INSTANCE = RepoUserModel()
    }

    companion object {
        val instance: RepoUserModel by lazy { Holder.INSTANCE }
    }

    private var userList = listOf<UserModel>()

    fun getUserList() = userList

    fun saveUserList(users: List<UserModel>) {
        userList = users
    }

    fun saveUser(user: UserModel, position: Int) {
        if (userList[position].id == user.id) {
            userList[position].name = user.name
            userList[position].email = user.email
            userList[position].company = user.company
            userList[position].repositoriesCount = user.repositoriesCount
            userList[position].hasDetails = true
        }
        else println("user no saved, no equals id")
    }

    fun printString() = userList.forEach { println(it.toString())}

    fun getUserDetailsOb(login: String) = App.getApi().userDetailsOb(login)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun requestAllUsersOb() =
            App.getApi().getUsersOb()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

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

