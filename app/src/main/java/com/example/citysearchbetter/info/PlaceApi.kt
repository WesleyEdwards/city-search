package com.example.citysearchbetter.info

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface PlaceApi {

    @GET("/countries")
    fun getListOfCountries(
        @HeaderMap headers: Map<String, String>,
    ): Single<CountryResponse>

    @GET("/countries/{countryCode}")
    fun getCountrySpecificInfo(
        @Path("countryCode") countryCode: String,
        @HeaderMap headers: Map<String, String>,
        @QueryMap parameters: Map<String, String>
    ): Single<Countries>
}