package com.example.View.UI

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.idnpv001.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivityView : AppCompatActivity() {

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view)


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
}