package com.example.citysearchbetter.utils

object Routes {
    const val dashboard = "dashboard"
    const val countryList = "countryList"
    const val searchCity = "searchCity"
    const val individualCountry = "individualCountry/{country}"
    val getCountry = { country: String -> "individualCountry/$country"}
}