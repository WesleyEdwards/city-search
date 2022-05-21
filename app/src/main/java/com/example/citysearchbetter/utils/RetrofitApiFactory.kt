package com.example.citysearchbetter.utils

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val httpClient: OkHttpClient = OkHttpClient.Builder().build()

interface ApiFactory {
    fun <T> createApiService(clazz: Class<T>): T
}

class RetrofitApiFactory(baseUrl: String):
    ApiFactory {

    private val retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()

    override fun <T> createApiService(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }
}