package com.example.programmer.trykotlin

import com.example.programmer.trykotlin.Constants.Companion.PER_PAGE
import com.example.programmer.trykotlin.model.SearchResultModel
import com.example.programmer.trykotlin.model.UserModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

interface APIService {
    @GET("users")
    fun getUsersOb(@Query("per_page") per_page: Int = PER_PAGE) : Observable<List<UserModel>>

    @GET("users")
    fun getUsersSince(@Query("since") since: Int, @Query("per_page") per_page: Int = PER_PAGE): Observable<List<UserModel>>
    @GET("users/{username}")
    fun userDetailsOb(@Path("username") userName: String): Observable<UserModel>

    @GET("search/users")
    fun searchUserPagination(@Query("q") query: String, @Query("page") page: Int, @Query("per_page") per_page: Int = PER_PAGE): Observable<SearchResultModel>

}