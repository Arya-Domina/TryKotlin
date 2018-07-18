package com.example.programmer.trykotlin

import android.app.Application
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
//import org.junit.runner.Request.method
import retrofit2.Response
import java.io.IOException


class App : Application() {
    private val BASE_URL = "https://api.github.com/"

    /*
    override fun onCreate() {

        val httpClient = OkHttpClient.Builder()
//        httpClient.addInterceptor(object : Interceptor() {
        httpClient.addInterceptor(Interceptor {chain ->
//            @Throws(IOException::class)
//            fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val request = original.newBuilder()
//                        .header("token", Pref(this@App).loadToken())
                        .method(original.method(), original.body())
                        .build()

                /*return*/ chain.proceed(request)
//            }
        })
        val client = httpClient.build()

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .client(client)
                .build()
        apiService = retrofit.create(APIService::class.java)
        println("Init")
        super.onCreate()
    }
*/

    companion object {
        private const val BASE_URL = "https://api.github.com"

        private val apiService: APIService by lazy {

            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .client(client)
                    .build()
            println("InitApi")
            return@lazy retrofit.create(APIService::class.java)
        }

//        fun initApi() {
//
//            val retrofit = Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
////                .client(client)
//                    .build()
//            apiService = retrofit.create(APIService::class.java)
////            println("Init")
//        }
//        fun getApi() = apiService
        fun getApi(): APIService {
            println("getApi")
            return apiService
        }

    }
}