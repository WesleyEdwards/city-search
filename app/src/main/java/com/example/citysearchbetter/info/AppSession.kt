package com.example.citysearchbetter.info

interface AppSession {
    val placeRepo: PlaceRepo
}

class Session(
    override val placeRepo: PlaceRepo,
): AppSession