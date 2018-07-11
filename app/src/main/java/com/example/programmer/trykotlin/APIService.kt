package com.example.programmer.trykotlin

import com.example.programmer.trykotlin.model.SearchResultModel
import com.example.programmer.trykotlin.model.UserModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("users")
    fun getUsers() : Call<List<UserModel>>

    @GET("")
    fun getUrls(): Call<String>

    @GET("/")
    fun getUrlsBody(): Call<ResponseBody>

    @GET("search/users")
    fun searchUser(@Query("q") query: String): Call<SearchResultModel>
}