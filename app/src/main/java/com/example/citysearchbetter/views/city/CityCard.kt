package com.example.citysearchbetter.views.city

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.citysearchbetter.info.Place

@Composable
fun CityCard(place: Place) {
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
                text = "${place.country}, ${place.state}",
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Preview
@Composable
fun PlaceCardPreview() {
    CityCard(
        Place("Logan", "Utah", "USA")
    )
}