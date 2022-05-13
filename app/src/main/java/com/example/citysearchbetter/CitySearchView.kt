package com.example.citysearchbetter

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import com.example.citysearchbetter.info.Place
import com.example.citysearchbetter.info.PlaceRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo

@Composable
fun CitySearchView(
    placeRepo: PlaceRepo
) {

    val disposer = disposer()
    var userInput by remember { mutableStateOf("") }
    var cityList = remember { mutableListOf<Place>() }


    Column {
        Text(
            text = "CitySearch",
            style = MaterialTheme.typography.h3
        )

        TextField(
            value = userInput,
            onValueChange = { userInput = it },
            label = { Text("city") }
        )

        Button(
            onClick = {
                placeRepo.getPlaceByCity(userInput)
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturn { listOf<Place>() }
                    .doOnError { throw it }
                    .doOnSuccess {
                        println("Success!")
                        cityList = placeRepo.getPlaceFromRepo().toMutableList()

                    }
                    .subscribe()
                    .addTo(disposer)
                println("Button has been clicked")

            },
        ) {
            Text("Search")
        }

        cityList = placeRepo.getPlaceFromRepo().toMutableList()
        cityList.forEach {
            println("CITY IS: ${it.city}")
        }

    }
}


@Composable
fun disposer(dependencies: Any? = null): CompositeDisposable {
    val composite = remember { CompositeDisposable() }
    DisposableEffect(dependencies) {
        println("disposer initialized")

        onDispose {
            println("Disposer disposed of stuff")
            composite.dispose()
        }
    }
    return composite
}