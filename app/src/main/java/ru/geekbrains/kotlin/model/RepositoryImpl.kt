package ru.geekbrains.kotlin.model

class RepositoryImpl : Repository {

    override fun getWeatherFromLocalStorage(): Weather {
        return Weather()
    }

    override fun getWeatherFromServer(): Weather {
        return Weather()
    }
}