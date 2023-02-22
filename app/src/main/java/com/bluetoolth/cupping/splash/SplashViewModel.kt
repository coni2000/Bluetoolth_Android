package com.bluetoolth.cupping.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by KimBH on 2023/02/22.
 */
class SplashViewModel : ViewModel() {
    private val _isSplashEnd = MutableLiveData(false)
    val isSplashEnd: LiveData<Boolean> get() = _isSplashEnd

    init {
        viewModelScope.launch {
            delay(2000)
            _isSplashEnd.postValue(true)
        }
    }
}