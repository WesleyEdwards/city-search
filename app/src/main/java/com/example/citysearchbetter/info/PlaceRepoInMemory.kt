package com.example.citysearchbetter.info

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.reactivex.rxjava3.core.Single

interface PlaceRepo {
    fun getPlaceByCity(city: String): Single<List<Place>>
    fun getPlaceFromRepo(): List<Place>
}
class PlaceRepoInMemory(
    private val api: PlaceApi
): PlaceRepo {

    private var cityList: MutableList<Place> = mutableListOf()


    override fun getPlaceByCity(city: String): Single<List<Place>> {
        val places = api.getByCity(
            headers = mapOf(
                "x-rapidapi-host" to "andruxnet-world-cities-v1.p.rapidapi.com",
                "x-rapidapi-key" to "1a80c5008dmsh9cfd3107d4c60fdp14ea41jsn5ec6952c5235"
            ),
            parameters = mapOf(
                "city" to city
            )
        )
        return places.map {
            updatePlaces(it)
            it
        }
    }
    override fun getPlaceFromRepo(): List<Place> {
        println("the list of cities is $cityList")
        return cityList
    }

    private fun updatePlaces(places: List<Place>) {
        this.cityList.clear()
        this.cityList.addAll(places)
    }
}