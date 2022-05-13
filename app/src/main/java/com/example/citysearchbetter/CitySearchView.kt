package com.example.citysearchbetter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.citysearchbetter.info.Place
import com.example.citysearchbetter.info.PlaceApi
import com.example.citysearchbetter.info.PlaceRepo
import com.example.citysearchbetter.info.PlaceRepoInMemory
import com.example.citysearchbetter.utils.RetrofitApiFactory
import com.example.citysearchbetter.utils.disposer
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo

val cityList = mutableStateListOf<Place>()

@Composable
fun CitySearchView(
    placeRepo: PlaceRepo
) {
    val disposer = disposer()
    var userInput by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier
                .padding(8.dp),
            text = "CitySearch",
            style = MaterialTheme.typography.h3
        )

        Row(
            Modifier
                .fillMaxWidth(1f)
                .padding(4.dp)
        ) {
            TextField(
                modifier = Modifier.padding(end = 4.dp),
                value = userInput,
                onValueChange = { userInput = it },
                label = { Text("city") }
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(IntrinsicSize.Max),
                onClick = {
                    placeRepo.getPlaceByCity(userInput)
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorReturn { listOf<Place>() }
                        .doOnError { throw it }
                        .doOnSuccess {
                            cityList.clear()
                            cityList.addAll(placeRepo.getPlaceFromRepo())
                        }
                        .subscribe()
                        .addTo(disposer)
                },
                enabled = userInput.length > 2
            ) {
                Text("Search")
            }
        }

        LazyColumn {
            cityList.forEach {
                item {
                    PlaceCard(it)
                }
            }
        }
    }
}

@Composable
fun PlaceCard(place: Place) {
    Column(
        Modifier
            .padding(12.dp)
            .fillMaxWidth(1f)
    ) {
        Text(
            text = place.city
        )
        Row(
            Modifier.padding(16.dp)
        ) {
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = "Country: ${place.country}",
                style = MaterialTheme.typography.caption
            )
            Text(
                text = "State: ${place.state}",
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Preview
@Composable
fun PlaceCardPreview() {
    PlaceCard(
        Place("Logan", "Utah", "USA")
    )
}

@Preview
@Composable
fun CitySearchViewPreview() {
    CitySearchView(
        placeRepo = PlaceRepoInMemory(
            RetrofitApiFactory().createApiService(PlaceApi::class.java)
        )
    )
}
