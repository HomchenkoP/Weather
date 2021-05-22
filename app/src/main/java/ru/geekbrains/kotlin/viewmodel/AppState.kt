package ru.geekbrains.kotlin.viewmodel

import ru.geekbrains.kotlin.model.Weather

sealed class AppState {
    data class Success(val weatherData: Weather) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}