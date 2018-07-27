package com.example.programmer.trykotlin

import android.app.Application
import com.example.programmer.trykotlin.Constants.Companion.HEADER_NAME
import com.example.programmer.trykotlin.Constants.Companion.HEADER_VALUE
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class App : Application() {
    companion object {
        private const val BASE_URL = "https://api.github.com"

        private val apiService: APIService by lazy {

            val okHttpClient = OkHttpClient.Builder().addInterceptor({ chain ->
                chain.proceed(chain.request()
                        .newBuilder()
                        .addHeader(HEADER_NAME, HEADER_VALUE)
                        .build())
            }).build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()
            println("InitApi")
            return@lazy retrofit.create(APIService::class.java)
        }

        fun getApi(): APIService {
            println("getApi")
            return apiService
        }
    }
}