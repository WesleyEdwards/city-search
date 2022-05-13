package com.example.citysearchbetter.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import io.reactivex.rxjava3.disposables.CompositeDisposable

@Composable
fun disposer(dependencies: Any? = null): CompositeDisposable {
    val composite = remember { CompositeDisposable() }
    DisposableEffect(dependencies) {
        println("disposer initialized")

        onDispose {
            println("Disposer disposed of stuff")
            composite.dispose()
        }
    }
    return composite
}