package com.example.citysearchbetter.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.citysearchbetter.CitySearchView
import com.example.citysearchbetter.R
import com.example.citysearchbetter.info.PlaceRepo
import com.example.citysearchbetter.ui.theme.CitysearchbetterTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun TabNav(
    placeRepo: PlaceRepo,
    navigateTo: (route: String) -> Unit
) {
    var tabIndex: Int by rememberSaveable { mutableStateOf(0) }

    val titleResource = listOf(
        "Home",
        "Search",
        "Notes"
    )

    val iconResources = listOf(
        R.drawable.ic_baseline_home_24,
        R.drawable.ic_baseline_search_24,
        R.drawable.ic_baseline_home_24,
    )

    CitysearchbetterTheme {
        Column(Modifier.fillMaxWidth(1f)) {
            Box(Modifier.weight(1f)) {
                when (tabIndex) {
                    0 -> home()
                    1 -> CitySearchView(placeRepo)
                    2 -> notes()
                }
            }
            TabRow(selectedTabIndex = tabIndex) {
                titleResource.forEachIndexed { index, resource ->
                    Tab(selected = index == tabIndex,
                        modifier = Modifier.background(Color.White)
                            .padding(8.dp),
                        onClick = { tabIndex = index },
                        icon = {
                            Icon(
                                painter = painterResource(id = iconResources[index]),
                                tint = Color.Gray,
                                modifier = Modifier.size(36.dp),
                                contentDescription = ""
                            )
                        }
                    )
                }
            }
        }
    }

}
