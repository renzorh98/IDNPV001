
package com.example.View.UI.Fragments.music

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.Model.Entities.Song
import com.example.View.UI.MainActivityView
import com.example.ViewModel.MusicViewModel
import com.example.idnpv001.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.concurrent.TimeUnit

class MusicFragment : Fragment(){
    private lateinit var musicViewModel: MusicViewModel
    private lateinit var songName: TextView
    private lateinit var songArtist: TextView
    private lateinit var songTime: TextView
    private lateinit var songCurrentTime : TextView
    private lateinit var songDuration: TextView
    private lateinit var currentSongAlbum: ImageView
    private lateinit var seekBar: SeekBar
    private lateinit var handler: Handler
    private lateinit var btnPlayPause: FloatingActionButton



    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        musicViewModel =
            ViewModelProvider(this).get(MusicViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_music, container, false)
        (activity as MainActivityView).playerList.updatePlayerList()


        val goToList:ImageView = root.findViewById(R.id.musicList)

        goToList.setOnClickListener{
            findNavController().navigate(R.id.navigation_music_list)
        }

        currentSongAlbum = root.findViewById(R.id.currentSongAlbum)
        var btnPrev: ImageView = root.findViewById(R.id.btnPrev)
        var btnSkip: ImageView = root.findViewById(R.id.btnSkip)
        //var btnLoop: ImageView = root.findViewById(R.id.btnLoop)
        //var btnSuffle: ImageView = root.findViewById(R.id.btnShuffle)
        btnPlayPause = root.findViewById(R.id.currentSongStatus)
        comprovePlayPause()

        btnPlayPause.setOnClickListener {
            (activity as MainActivityView).btn_play_pauseClicked()
            comprovePlayPause()
        }
        btnSkip.setOnClickListener{
            (activity as MainActivityView).btn_nextClicked(false)
        }
        btnPrev.setOnClickListener{
            (activity as MainActivityView).btn_prevClicked(false)
        }

        songName = root.findViewById(R.id.currentSongName)
        songArtist = root.findViewById(R.id.currentSongArtist)
        songTime = root.findViewById(R.id.currentSongTime)
        songDuration = root.findViewById(R.id.currentSongTotalTime)
        songCurrentTime = root.findViewById(R.id.currentSongTime)

        seekBar = root.findViewById(R.id.currentSongProgress)
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if(p2){
                    (activity as MainActivityView).musicService?.seekTo(p1)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
        updateSelectedSong()
        return root
    }

    fun updateSelectedSong(){
        if((activity as MainActivityView).musicService!!.isPlaying() || (activity as MainActivityView).musicService!!.isOnPause()){
            initialiseSeekBar()
        }

        var aux: Song = (activity as MainActivityView).playerList.getSongSelected()


        if(aux.mSongName.length > 32){
            songName.text = aux.mSongName.substring(0,32)+"..."
        }
        else{
            songName.text = aux.mSongName
        }

        if(aux.mSongName.length > 32){
            songName.text = aux.mSongName.substring(0,32)+"..."
        }
        else{
            songName.text = aux.mSongName
        }

        songArtist.text = aux.mSongArtist
        songDuration.text = aux.mSongDuration

        var image = aux.getAlbumArt(aux.fileIconPath!!)
        if(image != null){
            Glide.with(activity as MainActivityView).asBitmap().load(image).into(currentSongAlbum)
        }
        else{
            Glide.with(activity as MainActivityView).asBitmap().load(R.drawable.ic_musical_notes).into(currentSongAlbum)
        }
    }

    fun initialiseSeekBar(){
        seekBar.max = (activity as MainActivityView).musicService?.getDuration()!!

        handler = Handler()
        handler.postDelayed(object : Runnable{
            override fun run() {
                try {
                    seekBar.progress = (activity as MainActivityView).musicService?.getCurrentPosition()!!
                    songCurrentTime.text = transformTimes((activity as MainActivityView).musicService?.getCurrentPosition()!!.toLong())
                    handler.postDelayed(this, 1000)
                }catch (e: Exception){
                    seekBar.progress = 0
                }
            }
        }, 0)
    }

    private fun transformTimes(millis: Long):String{
        var duration = String.format("%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(millis),
            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))

        return duration
    }

    private fun comprovePlayPause(){
        if ((activity as MainActivityView).musicService!!.isPlaying()){
            btnPlayPause.setImageResource(R.drawable.ic_pause)
        }else{
            btnPlayPause.setImageResource(R.drawable.ic_play_arrow)
        }
    }






}