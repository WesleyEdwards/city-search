package com.example.citysearchbetter.utils

import com.example.citysearchbetter.info.*
import io.reactivex.rxjava3.core.Single

val previewSession = object : AppSession {
    override val placeRepo: PlaceRepo
        get() = TODO("Not yet implemented")

}

val previewCountry = Country(
    0L,
    "USA",
    "",
    Capital("", 0L),
    "",
    0L,
    Flag(""),
    "",
    Currency("", "")


)