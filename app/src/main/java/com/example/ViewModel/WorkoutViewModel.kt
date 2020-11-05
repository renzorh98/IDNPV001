package com.example.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WorkoutViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is workout Fragment"
    }
    val text: LiveData<String> = _text
}