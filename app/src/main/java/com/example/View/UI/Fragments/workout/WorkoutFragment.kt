package com.example.View.UI.Fragments.workout

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ViewModel.WorkoutViewModel
import com.example.idnpv001.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_workout.*


class WorkoutFragment : Fragment(), OnMapReadyCallback {


    private val LOCATION_PERMISSION_REQUEST_CODE = 2000
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        workoutViewModel =
            ViewModelProvider(this).get(WorkoutViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_workout, container, false)


        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mapFragment.onCreate(this.arguments)


        prepRequestLocationUpdates()

        return root
    }

    private fun prepRequestLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            workoutViewModel.getCurrentLocation()
        } else {
            val permissionRequest = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permissionRequest, LOCATION_PERMISSION_REQUEST_CODE)
        }
    }



    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            mMap = googleMap
        }
        // Add a marker in Sydney and move the camera

    }
}