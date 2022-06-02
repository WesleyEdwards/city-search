package com.example.citysearchbetter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.citysearchbetter.info.PlaceApi
import com.example.citysearchbetter.info.PlaceRepoInMemory
import com.example.citysearchbetter.info.Session
import com.example.citysearchbetter.info.WorldCitiesApi
import com.example.citysearchbetter.ui.theme.CitysearchbetterTheme
import com.example.citysearchbetter.utils.RetrofitApiFactory
import com.example.citysearchbetter.utils.Routes.dashboard
import com.example.citysearchbetter.utils.Routes.individualCountry
import com.example.citysearchbetter.views.IndividualCountry
import com.example.citysearchbetter.views.TabNav
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

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

                    val placeApiFactory =
                        RetrofitApiFactory("https://world-geo-data.p.rapidapi.com")
                    val worldCitiesApiFactory =
                        RetrofitApiFactory("https://andruxnet-world-cities-v1.p.rapidapi.com")
                    val placeApiHeaders = mapOf(
                        "X-RapidAPI-Host" to "world-geo-data.p.rapidapi.com",
                        "X-RapidAPI-Key" to "1a80c5008dmsh9cfd3107d4c60fdp14ea41jsn5ec6952c5235"
                    )
                    val cityApiHeaders = mapOf(
                        "x-rapidapi-host" to "andruxnet-world-cities-v1.p.rapidapi.com",
                        "x-rapidapi-key" to "1a80c5008dmsh9cfd3107d4c60fdp14ea41jsn5ec6952c5235"
                    )

                    val placeRepo = PlaceRepoInMemory(
                        placeApiFactory.createApiService(PlaceApi::class.java),
                        worldCitiesApiFactory.createApiService(WorldCitiesApi::class.java),
                        placeApiHeaders,
                        cityApiHeaders
                    )

                    val sessionInfo = Session(placeRepo)

                    val nav = rememberAnimatedNavController()

                    AnimatedNavHost(navController = nav, startDestination = dashboard) {
                        composable(dashboard) {
                            TabNav(
                                navigateTo = { route -> nav.navigate(route) },
                                sessionInfo = sessionInfo
                            )
                        }

                        composable(individualCountry,
                            enterTransition =
                            { slideInTransition() },
                            exitTransition = { slideOutTransition() }
                        ) { backStackEntry ->
                            IndividualCountry(
                                sessionInfo,
                                backStackEntry.arguments?.getString("country") ?: "",
                            ) { nav.navigate(dashboard) }
                        }
                    }
                }
            }
        }
    }

    private fun slideInTransition(): EnterTransition {
        return slideInHorizontally { resources.displayMetrics.heightPixels }
    }

    private fun slideOutTransition(): ExitTransition {
        return slideOutHorizontally { resources.displayMetrics.heightPixels }
    }
}
