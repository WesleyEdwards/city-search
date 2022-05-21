package com.example.citysearchbetter.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.citysearchbetter.R
import com.example.citysearchbetter.info.AppSession
import com.example.citysearchbetter.info.Session
import com.example.citysearchbetter.ui.theme.CitysearchbetterTheme

@Composable
fun TabNav(
    sessionInfo: AppSession,
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
        R.drawable.ic_baseline_note_24,
    )

    CitysearchbetterTheme {
        Column(Modifier.fillMaxWidth(1f)) {
            Box(Modifier.weight(1f)) {
                when (tabIndex) {
                    0 -> HomeView()
                    1 -> CitySearchView(sessionInfo)
                    2 -> NotesView()
                }
            }

            Divider()

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
