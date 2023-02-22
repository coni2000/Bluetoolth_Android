package com.bluetoolth.cupping.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by KimBH on 2023/02/20.
 */
class MainViewModel : ViewModel() {
    private val _url = MutableLiveData<String>()
    val url: LiveData<String> get() = _url
}