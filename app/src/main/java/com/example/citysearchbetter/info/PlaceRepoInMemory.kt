package com.example.citysearchbetter.info

import com.example.citysearchbetter.utils.previewCountry
import io.reactivex.rxjava3.core.Single

interface PlaceRepo {
    fun getListOfCountries(): Single<List<CountryRef>>
    fun getPlaceByCity(city: String): Single<List<Place>>
    fun getIndividualCountryInfo(countryId: String): Single<Country>
    fun getPlaceFromRepo(): List<Place>
    fun getCountriesFromRepo(filterName: String): List<CountryRef>
    fun getCountryFromRepo(countryId: String): Country
}

class PlaceRepoInMemory(
    private val placesApi: PlaceApi,
    private val worldCitiesApi: WorldCitiesApi,
    private val placesApiHeaders: Map<String, String>,
    private val cityApiHeaders: Map<String, String>
) : PlaceRepo {

    private var cityList: MutableList<Place> = mutableListOf()
    private var countryList: MutableList<CountryRef> = mutableListOf()
    private var specificCountries: MutableList<Country> = mutableListOf()

    override fun getListOfCountries(): Single<List<CountryRef>> {
        return if (countryList.isEmpty()) {
            placesApi.getListOfCountries(
                headers = placesApiHeaders
            ).map {
                updateCountryRefList(it.countries)
                it.countries
            }
        } else {
            Single.just(countryList.toList())
        }
    }

    override fun getPlaceByCity(city: String): Single<List<Place>> {
        return worldCitiesApi.getByCity(
            headers = cityApiHeaders,
            parameters = mapOf(
                "query" to city,
                "searchby" to "city"
            )
        ).map {
            updatePlaces(it)
            it
        }
    }

    override fun getIndividualCountryInfo(countryId: String): Single<Country> {
        return if (specificCountries.any { it.code == countryId }) {
            println("API is not called. YAY!")
            Single.just(specificCountries.first { it.code == countryId })
        } else {
            placesApi.getCountrySpecificInfo(
                countryCode = countryId,
                headers = placesApiHeaders,
            ).map {
                specificCountries.add(it)
                it
            }
        }
    }

    override fun getPlaceFromRepo(): List<Place> {
        return cityList
    }

    override fun getCountriesFromRepo(filterName: String): List<CountryRef> {
        return countryList.filter {
            it.name.subSequence(0, filterName.length)
                .map { c -> c.lowercase() } ==
                    filterName
                        .map { c -> c.lowercase() }
        }
    }

    override fun getCountryFromRepo(countryId: String): Country {
        return specificCountries.firstOrNull {
            it.code == countryId
        } ?: previewCountry
    }

    private fun updatePlaces(places: List<Place>) {
        this.cityList.clear()
        this.cityList.addAll(places)
    }

    private fun updateCountryRefList(countries: List<CountryRef>) {
        this.countryList.clear()
        this.countryList.addAll(countries)
    }
}