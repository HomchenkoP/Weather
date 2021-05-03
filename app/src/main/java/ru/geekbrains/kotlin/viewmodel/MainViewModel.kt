package ru.geekbrains.kotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep

class MainViewModel(private val liveDataToObserve: MutableLiveData<Any> = MutableLiveData()) : ViewModel() {

    fun getLiveData(): LiveData<Any> {
        getDataFromLocalSource()
        return liveDataToObserve
    }

    private fun getDataFromLocalSource() {
        // имитация запроса к БД
        Thread {
            sleep(3000)
            liveDataToObserve.postValue(Any())
        }.start()
    }
}