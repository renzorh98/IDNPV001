package com.example.View.UI

import android.Manifest
import android.annotation.SuppressLint
import android.content.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.Model.Entities.PlayerList
import com.example.Model.Interface.IActionPlaying
import com.example.Model.Services.MusicService
import com.example.View.UI.Fragments.music.MusicFragment
import com.example.idnpv001.R
import com.google.android.material.bottomnavigation.BottomNavigationView


@Suppress("IMPLICIT_BOXING_IN_IDENTITY_EQUALS")
class MainActivityView : AppCompatActivity(), ServiceConnection, IActionPlaying{

    lateinit var playerList: PlayerList
    var musicService: MusicService? = null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view)
        requestLocationPermissions()
        requestReadStoragePermissions()

        playerList = PlayerList(applicationContext)
        Log.e("asdasd", ""+playerList.getSize())

        var intent: Intent = Intent(applicationContext, MusicService::class.java)
        bindService(intent, this, Context.BIND_AUTO_CREATE)
        //musicService?.createMediaPlayer(playerList.getSongSelected().mFilePath)


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

    private fun requestReadStoragePermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) !==
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
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

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        unbindService(this)
    }


    override fun onServiceDisconnected(p0: ComponentName?) {
        musicService = null
        Log.e("asdasd", "onServiceDisconnected")

    }

    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
        var myBinder: MusicService.MyBinder = p1 as MusicService.MyBinder
        musicService = myBinder.getService()
        Log.e("asdasd", "onServiceConnected")

    }

    override fun btn_play_pauseClicked() {
        if(musicService!!.isPlaying()){
            musicService!!.pause()
        } else if(musicService!!.isOnPause()){
            musicService!!.start()
        }
        else{
            musicService?.createMediaPlayer(playerList.getSongSelected().mFilePath)
            musicService?.mediaPlayer?.setOnCompletionListener{
                btn_nextClicked(true)

            }//check but noy only here
            musicService!!.start()}
    }

    override fun btn_prevClicked(type: Boolean) {
        if(musicService!!.isPlaying()){
            playerList.prevSelectList()
            musicService?.createMediaPlayer(playerList.getSongSelected().mFilePath)
            musicService?.mediaPlayer?.setOnCompletionListener{
                btn_nextClicked(true)

            }
            try{
                var fragment = getForegroundFragment()
                (fragment as MusicFragment).updateSelectedSong()
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }else{
            playerList.prevSelectList()
            if(type){
                musicService?.createMediaPlayer(playerList.getSongSelected().mFilePath)
                musicService?.mediaPlayer?.setOnCompletionListener{
                    btn_nextClicked(true)

                }
                musicService?.start()
            }
            try{
                var fragment = getForegroundFragment()
                (fragment as MusicFragment).updateSelectedSong()
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    override fun btn_nextClicked(type: Boolean) {
        if(musicService!!.isPlaying()){
            playerList.skipSelectList()
            musicService?.createMediaPlayer(playerList.getSongSelected().mFilePath)
            musicService?.mediaPlayer?.setOnCompletionListener{
                btn_nextClicked(true)

            }
            try{
                var fragment = getForegroundFragment()
                (fragment as MusicFragment).updateSelectedSong()
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }else{
            playerList.skipSelectList()
            if(type){
                musicService?.createMediaPlayer(playerList.getSongSelected().mFilePath)
                musicService?.mediaPlayer?.setOnCompletionListener{
                    btn_nextClicked(true)

                }
                musicService?.start()
            }
            try{
                var fragment = getForegroundFragment()
                (fragment as MusicFragment).updateSelectedSong()
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    override fun playlist_itemClicked(_path: String) {
        musicService?.createMediaPlayer(_path)
        musicService?.mediaPlayer?.setOnCompletionListener{
            btn_nextClicked(true)

        }
        musicService!!.start()
    }

    private fun getForegroundFragment(): Fragment? {
        var navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        return if (navHostFragment == null) null else navHostFragment.getChildFragmentManager().getFragments().get(0);
    }
}