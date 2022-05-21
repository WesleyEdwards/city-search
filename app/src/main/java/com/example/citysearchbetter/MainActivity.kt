package com.example.citysearchbetter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.citysearchbetter.info.PlaceApi
import com.example.citysearchbetter.info.PlaceRepoInMemory
import com.example.citysearchbetter.ui.theme.CitysearchbetterTheme
import com.example.citysearchbetter.utils.RetrofitApiFactory
import com.example.citysearchbetter.utils.Routes.home
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

                    val apiFactory = RetrofitApiFactory("https:///")
                    val placesRepo = PlaceRepoInMemory(
                        apiFactory.createApiService(PlaceApi::class.java)
                    )


                    val nav = rememberAnimatedNavController()

                    AnimatedNavHost(navController = nav, startDestination = home) {
                        composable(home) {
                            TabNav(
                                navigateTo = { route -> nav.navigate(route) },
                                placeRepo = placesRepo
                            )
                        }
                    }

                    CitySearchView(placesRepo)

                }
            }
        }
    }
}
