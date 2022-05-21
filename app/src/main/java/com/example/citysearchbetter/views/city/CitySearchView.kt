package com.example.citysearchbetter.views

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.citysearchbetter.R
import com.example.citysearchbetter.info.AppSession
import com.example.citysearchbetter.info.Place
import com.example.citysearchbetter.utils.disposer
import com.example.citysearchbetter.utils.previewSession
import com.example.citysearchbetter.views.city.CityCard
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo

val cityList = mutableStateListOf<Place>()


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CitySearchView(
    sessionInfo: AppSession
) {
    var userInput by rememberSaveable { mutableStateOf("") }
    val disposer = disposer()
    val repo = sessionInfo.placeRepo
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current


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
                label = { Text(stringResource(R.string.city)) }
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(IntrinsicSize.Max),
                onClick = {
                    repo.getPlaceByCity(userInput)
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorReturn { listOf() }
                        .doOnError { throw it }
                        .doOnSuccess {
                            keyboardController?.hide()

                            cityList.clear()
                            cityList.addAll(repo.getPlaceFromRepo())

                            if (cityList.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    R.string.no_city_found,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        .subscribe()
                        .addTo(disposer)
                },
                enabled = userInput.length > 2
            ) {
                Text(stringResource(R.string.search))
            }
        }

        LazyColumn {
            cityList.forEach {
                item { CityCard(it) }
            }
        }
    }

}


@Preview
@Composable
fun CitySearchViewPreview() {
    CitySearchView(
        previewSession
    )
}