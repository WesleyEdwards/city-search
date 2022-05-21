package com.example.citysearchbetter.info

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.QueryMap

interface WorldCitiesApi {
    @GET("/")
    fun getByCity(
        @HeaderMap headers: Map<String, String>,
        @QueryMap parameters: Map<String, String>
    ): Single<List<Place>>

}