package com.example.citysearchbetter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.citysearchbetter.info.*
import com.example.citysearchbetter.ui.theme.CitysearchbetterTheme
import com.example.citysearchbetter.utils.RetrofitApiFactory
import com.example.citysearchbetter.utils.Routes.countryList
import com.example.citysearchbetter.utils.Routes.home
import com.example.citysearchbetter.utils.Routes.individualCountry
import com.example.citysearchbetter.utils.Routes.searchCity
import com.example.citysearchbetter.views.TabNav
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CitysearchbetterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val placeApiFactory = RetrofitApiFactory("https://world-geo-data.p.rapidapi.com")
                    val worldCitiesApiFactory = RetrofitApiFactory("https://andruxnet-world-cities-v1.p.rapidapi.com")

                    val placeRepo = PlaceRepoInMemory(
                        placeApiFactory.createApiService(PlaceApi::class.java),
                        worldCitiesApiFactory.createApiService(WorldCitiesApi::class.java)
                    )

                    val sessionInfo = Session(placeRepo)

                    val nav = rememberAnimatedNavController()

                    AnimatedNavHost(navController = nav, startDestination = home) {
                        composable(home) {
                            TabNav(
                                navigateTo = { route -> nav.navigate(route) },
                                sessionInfo = sessionInfo
                            )
                        }
                    }
                }
            }
        }
    }
}
