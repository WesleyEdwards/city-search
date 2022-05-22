package com.example.citysearchbetter.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.citysearchbetter.R
import com.example.citysearchbetter.info.AppSession
import com.example.citysearchbetter.info.Country
import com.example.citysearchbetter.utils.previewSession


@Composable
fun IndividualCountry(
    session: AppSession,
    countryId: String,
    onDone: () -> Unit
) {
    val countryInfo = session.placeRepo.getCountryFromRepo(countryId)

    val view = remember { mutableStateOf(0) }

    when (view.value) {
        0 -> CountryView(
            countryInfo,
            wikipedia = { view.value += 1 },
            onDone = { onDone() }
        )
        else -> WikipediaView(countryInfo) { view.value = 0 }
    }


}

@Composable
fun CountryView(
    countryInfo: Country,
    wikipedia: () -> Unit,
    onDone: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            Button(
                content = {
                    Text("Additional Reading")
                },
                onClick = {
                    println("Additional Reading")
                    wikipedia()
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Column() {
            Box(Modifier.fillMaxWidth()) {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_arrow_back_24),
                    tint = Color.Gray,
                    contentDescription = "back",
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .clickable { onDone() }
                        .size(24.dp)
                        .padding(16.dp)
                )
                Text(
                    text = countryInfo.name,
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopCenter),
                )
            }

            Text(
                "Capital: ${countryInfo.capital.name}",
                modifier = Modifier.padding(16.dp)
            )
            Text(
                "Population: ${countryInfo.population}",
                modifier = Modifier.padding(16.dp)
            )
            Text(
                "Currency: ${countryInfo.currency.name}",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
@Preview
fun IndividualCountryPreview() {
    IndividualCountry(previewSession, "") {}
}