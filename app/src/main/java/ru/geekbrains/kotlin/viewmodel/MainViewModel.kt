package ru.geekbrains.kotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep

import ru.geekbrains.kotlin.model.LoadingException
import ru.geekbrains.kotlin.model.Repository
import ru.geekbrains.kotlin.model.RepositoryImpl

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<AppState> {
        return liveDataToObserve
    }

    fun getWeather() {
        getDataFromLocalSource()
    }

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        // имитация запроса к БД
        Thread {
            // три попытки
            for (tryCnt in 1..3) {
                liveDataToObserve.postValue(AppState.Loading)
                sleep(3000)
                try {
                    liveDataToObserve.postValue(AppState.Success(repositoryImpl.getWeatherFromLocalStorage()))
                    break
                } catch (e: LoadingException) {
                    liveDataToObserve.postValue(AppState.Error(e))
                    sleep(1000)
                }
            }
        }.start()
    }
}