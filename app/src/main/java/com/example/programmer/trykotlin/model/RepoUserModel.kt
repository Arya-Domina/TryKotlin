package com.example.programmer.trykotlin.model

import com.example.programmer.trykotlin.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoUserModel {
    private var userList = listOf<UserModel>()

//    fun saveUsers(users: List<UserModel>) {
//        userList = users
//    }

    fun getString() = userList.forEach { println(it.toString())}

    fun getUserById(id: Int) = userList.find { it.id == id }

    fun getAllUsers() = userList

    fun requestAllUsers() =
        App.getApi().getUsers().enqueue(object: Callback<List<UserModel>> {
            override fun onFailure(call: Call<List<UserModel>>?, t: Throwable?) {
                println("getUsers onFailure")
                println(t)
            }

            override fun onResponse(call: Call<List<UserModel>>?, response: Response<List<UserModel>>?) {
                println("getUsers onResponse")
                println("size ${response?.body()?.size}")
                response?.body()?.let { it1 -> userList = it1 }
            }
        })

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