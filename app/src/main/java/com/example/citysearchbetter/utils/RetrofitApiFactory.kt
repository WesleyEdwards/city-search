package com.example.citysearchbetter.utils

import com.example.citysearchbetter.ApiFactory
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val httpClient: OkHttpClient = OkHttpClient.Builder().build()

class RetrofitApiFactory(baseUrl: String = "https://andruxnet-world-cities-v1.p.rapidapi.com"):
    ApiFactory {

    private val retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()

    override fun <T> createApiService(clazz: Class<T>): T {
        println("${retrofit.baseUrl()} is the base url")
        return retrofit.create(clazz)
    }
}