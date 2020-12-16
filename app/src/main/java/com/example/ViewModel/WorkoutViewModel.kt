package com.example.ViewModel

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import com.example.View.UI.Fragments.workout.LocationLiveData
import com.example.dto.Coordinate
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
    private val locationLiveData = LocationLiveData(application)
    internal fun getLocationLiveData() = locationLiveData


    fun getCurrentLocation(mMap: GoogleMap){

        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                mMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(it.latitude, it.longitude))
                        .title("My position"))
            }

    }
}