package com.example.programmer.trykotlin.model

import com.example.programmer.trykotlin.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers

class RepoUserModel {
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

    fun getUserById(id: Int): UserModel? {
        var user = userList.find { it.id == id }
//        val index = userList.indexOf(user)
        if (user != null && !user.hasDetails && user.login != null)
            App.getApi().userDetails(user.login ?: "").enqueue(object: Callback<UserModel> {
                override fun onFailure(call: Call<UserModel>?, t: Throwable?) {
                    println("userDetails onFailure")
                    println(t)
                }

                override fun onResponse(call: Call<UserModel>?, response: Response<UserModel>?) {
                    println("userDetails onResponse")
//                    userList[index] = response?.body()
                    user = response?.body()
//                    val newUser = response?.body()
//                    userList[index].name = newUser?.name.toString()
//                    userList[index].email = newUser?.email.toString()
//                    userList[index].company = newUser?.company.toString()
//                    userList[index].repositoriesCount = newUser?.repositoriesCount ?: 0
//                    userList[index].hasDetails = true
                    println("getUserById list $userList")
                }
            })

        return user
    }

//    fun getUserByUsername(username: String): UserModel? {
//        App.getApi().userDetailsOb(username)
//    }

    fun getUserDetailsOb(login: String) = App.getApi().userDetailsOb(login)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getUserDetails(username: String) =//
            App.getApi().userDetails(username).enqueue(object: Callback<UserModel> {
                override fun onFailure(call: Call<UserModel>?, t: Throwable?) {
                    println("userDetails onFailure")
                    println(t)
                }

                override fun onResponse(call: Call<UserModel>?, response: Response<UserModel>?) {
                    println("userDetails onResponse")
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })


    fun requestAllUsersOb() =
            App.getApi().getUsersOb()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

//        App.getApi().getUsers().enqueue(object: Callback<List<UserModel>> {
//            override fun onFailure(call: Call<List<UserModel>>?, t: Throwable?) {
//                println("getUsers onFailure")
//                println(t)
//            }
//
//            override fun onResponse(call: Call<List<UserModel>>?, response: Response<List<UserModel>>?) {
//                println("getUsers onResponse")
//                println("size ${response?.body()?.size}")
//                response?.body()?.let { it1 -> userList = it1 }
//            }
//        })

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

