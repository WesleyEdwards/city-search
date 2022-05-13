package com.example.citysearchbetter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.citysearchbetter.info.PlaceApi
import com.example.citysearchbetter.info.PlaceRepoInMemory
import com.example.citysearchbetter.ui.theme.CitysearchbetterTheme
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface ApiFactory {
    fun <T> createApiService(clazz: Class<T>): T
}

val httpClient: OkHttpClient = OkHttpClient.Builder().build()

class RetrofitApiFactory(baseUrl: String = "https://andruxnet-world-cities-v1.p.rapidapi.com"): ApiFactory {

    private val retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()

    override fun <T> createApiService(clazz: Class<T>): T {
        println("${retrofit.baseUrl()} is the base url")
        return retrofit.create(clazz)
    }
}



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CitysearchbetterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val apiFactory = RetrofitApiFactory(
                        "https://andruxnet-world-cities-v1.p.rapidapi.com"
                    )
                    val repo = PlaceRepoInMemory(
                        apiFactory.createApiService(PlaceApi::class.java)
                    )

                    CitySearchView(repo)


                }
            }
        }
    }
}
