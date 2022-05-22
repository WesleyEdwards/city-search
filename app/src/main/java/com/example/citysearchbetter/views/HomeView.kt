package com.example.citysearchbetter.views

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.citysearchbetter.R
import com.example.citysearchbetter.info.AppSession
import com.example.citysearchbetter.info.CountryRef
import com.example.citysearchbetter.ui.theme.CitysearchbetterTheme
import com.example.citysearchbetter.utils.Routes
import com.example.citysearchbetter.utils.disposer
import com.example.citysearchbetter.utils.previewSession
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo


val countryList = mutableStateListOf<CountryRef>()

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeView(
    session: AppSession,
    navigateTo: (route: String) -> Unit,
) {
    val disposer = disposer()
    var userInput by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    if (countryList.isEmpty()) {
        session
            .placeRepo
            .getListOfCountries()
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn { listOf() }
            .doOnError { throw it }
            .doOnSuccess {
                countryList.addAll(session.placeRepo.getCountriesFromRepo(""))
            }
            .subscribe()
            .addTo(disposer)
    }



    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = stringResource(R.string.country_list),
            modifier = Modifier.padding(16.dp),
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
                label = { Text("Country") }
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(IntrinsicSize.Max),
                onClick = {
                    keyboardController?.hide()
                    countryList.clear()
                    countryList.addAll(session.placeRepo.getCountriesFromRepo(userInput))
                }
            ) {
                Text(stringResource(R.string.search))
            }
        }

        LazyColumn {
            countryList.forEach {
                item { CountryCard(it, navigateTo, session) }
            }
        }
    }
}

@Composable
fun CountryCard(
    country: CountryRef,
    navigateTo: (route: String) -> Unit,
    session: AppSession
) {
    val disposer = disposer()
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable {
                session.placeRepo.getIndividualCountryInfo(country.code)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess {
                        navigateTo(Routes.getCountry(country.code))
                    }
                    .subscribe()
                    .addTo(disposer)
            },
    ) {
        Text(country.name)
        Icon(
            painter = painterResource(R.drawable.ic_baseline_navigate_next_24),
            tint = Color.Gray,
            contentDescription = "Next",
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
    Divider(Modifier.padding(horizontal = 16.dp))
}

@Composable
@Preview
fun HomeViewPreview() {
    CitysearchbetterTheme {
        HomeView(previewSession) {}
    }
}