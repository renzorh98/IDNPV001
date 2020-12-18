package com.example.View.UI.Fragments.workout

import android.app.Application
import android.location.Location
import androidx.lifecycle.LiveData
import com.example.dto.Coordinate
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class LocationLiveData(application: Application) : LiveData<Coordinate>() {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    override fun onActive() {
        super.onActive()
        fusedLocationClient.lastLocation.addOnSuccessListener {
                location: Location  -> location.also {
                        setLocationData(it)
                }
        }
        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            if(locationResult == null){
                return
            }
            for (location in locationResult.locations) {
                if(location!=null){
                    setLocationData(location)
                }
            }
        }
    }

    private fun setLocationData(location: Location) {
        value = Coordinate(location.longitude, location.latitude)
    }

    companion object {
        val ONE_MINUTE : Long = 60000
        val locationRequest : LocationRequest = LocationRequest.create().apply {
            interval = ONE_MINUTE
            fastestInterval = ONE_MINUTE/4
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

    }
}