package com.example.programmer.trykotlin

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import com.example.programmer.trykotlin.Constants.Companion.HEADER_NAME
import com.example.programmer.trykotlin.Constants.Companion.HEADER_VALUE
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        private const val BASE_URL = "https://api.github.com"

        var context: Context? = null

        private fun createApiService(): APIService {
            val okHttpClientBuilder = OkHttpClient.Builder()
            val token = PreferenceManager.getDefaultSharedPreferences(context).getString(HEADER_VALUE, "")
            if (token != "")
                okHttpClientBuilder.addInterceptor({ chain ->
                    chain.proceed(chain.request()
                            .newBuilder()
                            .addHeader(HEADER_NAME, HEADER_VALUE +
                                    PreferenceManager.getDefaultSharedPreferences(context).getString(HEADER_VALUE, ""))
                            .build())
                })
//                        .build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClientBuilder.build())
                    .build()
            println("InitApi")
            return retrofit.create(APIService::class.java)
        }

        private val apiService: APIService by lazy {
            println("createApi")
            return@lazy createApiService()
        }

        fun getApi(): APIService {
            println("getApi")
            return apiService
        }
    }
}