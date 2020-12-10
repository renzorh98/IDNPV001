package com.example.View.UI

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.idnpv001.R
import com.google.android.material.bottomnavigation.BottomNavigationView


@Suppress("IMPLICIT_BOXING_IN_IDENTITY_EQUALS")
class MainActivityView : AppCompatActivity() {

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view)
        requestLocationPermissions()


        var PRIVATE_MODE = Context.MODE_PRIVATE
        val PREF_NAME = "ActiveUser"
        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        val namAct: String? = sharedPref.getString("name", "0")

        if(namAct.equals("0")){
            val navView: BottomNavigationView = findViewById(R.id.nav_view)
            navView.visibility = View.INVISIBLE

            val navController = findNavController(R.id.nav_host_fragment)
            val navGraph: NavGraph = navController.graph
            navGraph.startDestination = R.id.navigation_login_ini
            navController.setGraph(navGraph)
        }
        else {
            val navView: BottomNavigationView = findViewById(R.id.nav_view)
            navView.visibility = View.VISIBLE

            val navController = findNavController(R.id.nav_host_fragment)
            val navGraph: NavGraph = navController.graph
            navGraph.startDestination = R.id.navigation_history
            navController.setGraph(navGraph)

            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.navigation_history, R.id.navigation_workout, R.id.navigation_music
                )
            )
            //setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }
    }

    private fun requestLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !==
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) ===
                                PackageManager.PERMISSION_GRANTED)) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
}