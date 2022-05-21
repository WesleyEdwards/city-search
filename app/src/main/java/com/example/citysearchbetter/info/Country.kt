package com.example.citysearchbetter.info

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    val countries: List<Country>
)

data class Country(
    @SerializedName("geonameid") val geonameId: Long,
    val name: String,
    val code: String,
    val capital: Capital,
    @SerializedName("area_size") val areaSize: String,
    val population: Long,
    val flag: Flag,
    @SerializedName("wiki_url") val wikiUrl: String,
    val currency: Currency,
)

data class Capital(
    val name: String,
    val geonameId: Long
)

data class Flag(
    val file: String
)

data class Currency(
    val code: String,
    val name: String
)