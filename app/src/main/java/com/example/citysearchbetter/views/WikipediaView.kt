package com.example.citysearchbetter.views

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.citysearchbetter.R
import com.example.citysearchbetter.info.Country

@Composable
fun WikipediaView(
    country: Country,
    onBack: () -> Unit
) {


    Column {
        Box(Modifier.fillMaxWidth()) {
            Icon(
                painter = painterResource(R.drawable.ic_baseline_arrow_back_24),
                tint = Color.Gray,
                contentDescription = "back",
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .clickable { onBack() }
                    .size(24.dp)
                    .padding(start =  16.dp, top = 16.dp, bottom = 32.dp, end = 16.dp)
            )
            AndroidView(factory = {
                WebView(it).apply {
//                    layoutParams = ViewGroup.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT
//                    )
                    webViewClient = WebViewClient()
                    loadUrl(country.wikiUrl)
                }
            }, update = {
                it.loadUrl(country.wikiUrl)
            })

        }
    }
}