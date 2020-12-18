package com.example.ViewModel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi

import androidx.lifecycle.AndroidViewModel
import com.example.Model.database.AppDatabase
import com.example.Repository.LoginRepository
import com.example.View.UI.Fragments.workout.LocationLiveData
import com.example.dto.Coordinate
import com.example.dto.Training
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DatabaseReference
import java.time.LocalDateTime
import java.util.*


class WorkoutViewModel(application: Application) : AndroidViewModel(application) {
    var lgRepository = LoginRepository(application)
    private val locationLiveData = LocationLiveData(application)
    internal fun getLocationLiveData() = locationLiveData
    var database: AppDatabase = AppDatabase()
    private var ref: DatabaseReference
    init {
        ref = database.getReference()
    }

    fun addTraining():Training? {
        val key = ref.child("trainings").push().key ?: return null
        var training = Training(key, Calendar.getInstance().time.time,0.1,"", "run")
        ref.child("trainings").child(key).setValue(training)
        addUserWithTraining(key)
        return training
    }

    private fun addUserWithTraining(key: String?) {
        var user:String = lgRepository.getCurrentUser()
        if(key!=null){
            ref.child("userWithTrainings").child(user).child(key).setValue(true)
        }
    }
    fun addCoordinateToTraining(key: String?, coordinate: Coordinate){
        if(key!=null){
            val coordinateKey = ref.child("trainings").push().key ?: return
            ref.child("trainings").child(key).child("coordinates").child(coordinateKey).setValue(coordinate)
        }
    }

    fun setAttributesTraining(training: Training?, text: CharSequence?, distance: Double) {
        if(training != null){
            ref.child("trainings").child(training.trainingId).child("time").setValue(text)
            ref.child("trainings").child(training.trainingId).child("distance").setValue(distance)
        }
    }

}