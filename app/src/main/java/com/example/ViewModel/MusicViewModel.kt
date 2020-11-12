package com.example.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MusicViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is music Fragment"
    }

    private val _greeting: MutableLiveData<String> = MutableLiveData<String>().apply {
        value = "From viewModel Greeting"
    }
    val text: LiveData<String> = _text
    val greeting: LiveData<String> = _greeting
}