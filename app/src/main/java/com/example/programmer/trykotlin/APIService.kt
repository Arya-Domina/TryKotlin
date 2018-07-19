package com.example.programmer.trykotlin

import com.example.programmer.trykotlin.model.SearchResultModel
import com.example.programmer.trykotlin.model.UserModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

interface APIService {
    @GET("users")
    fun getUsers() : Call<List<UserModel>>

    @GET("users")
    fun getUsersOb() : Observable<List<UserModel>>

    @GET("")
    fun getUrls(): Call<String>

    @GET("/")
    fun getUrlsBody(): Call<ResponseBody>

    @GET("search/users")
    fun searchUser(@Query("q") query: String): Call<SearchResultModel>

    @GET("search/users")
    fun searchUserOb(@Query("q") query: String): Observable<SearchResultModel>

    @GET("users/{username}")
    fun userDetails(@Path("username") userName: String): Call<UserModel>

    @GET("users/{username}")
    fun userDetailsOb(@Path("username") userName: String): Observable<UserModel>

}