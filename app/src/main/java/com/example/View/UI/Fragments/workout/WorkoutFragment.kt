package com.example.View.UI.Fragments.workout

import android.content.Context.LOCATION_SERVICE
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ViewModel.WorkoutViewModel
import com.example.idnpv001.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_workout.*

class WorkoutFragment : Fragment(), OnMapReadyCallback {

    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var googleMap: GoogleMap
    lateinit var ubicacion:LocationFinder

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mapView2.onCreate(savedInstanceState)
        mapView2.onResume()

        mapView2.getMapAsync(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        workoutViewModel =
            ViewModelProvider(this).get(WorkoutViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_workout, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_dashboard)

        workoutViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        ubicacion = LocationFinder(root.context)

        return root
    }

    override fun onMapReady(map: GoogleMap?) {

        map?.let {
            googleMap = it

            val arequipa = LatLng(ubicacion.latitud, ubicacion.longitud)
            googleMap.addMarker(MarkerOptions().position(arequipa).title("Arequipa"))

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(arequipa))

        }
    }


}