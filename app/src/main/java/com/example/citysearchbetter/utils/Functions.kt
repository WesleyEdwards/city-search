package com.example.citysearchbetter.utils

import androidx.compose.runtime.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single


@Composable
inline fun <S, T: Any> S.subscribeAsState(
    initial: T,
    crossinline makeObservable: () -> Observable<T>
): State<T> {
    val state = remember { mutableStateOf(initial) }
    DisposableEffect(this) {
        val disposable = makeObservable().subscribe {
            state.value = it
        }
        onDispose {
            disposable.dispose()
        }
    }
    return state
}

@Composable
inline fun <S, T: Any> S.subscribeAsStateSingle(
    initial: T,
    crossinline makeObservable: S.() -> Single<T>
): State<T> {
    val state = remember { mutableStateOf(initial) }
    DisposableEffect(this) {
        val disposable = makeObservable(this@subscribeAsStateSingle).subscribe { it ->
            state.value = it
        }
        onDispose {
            disposable.dispose()
        }
    }
    return state
}