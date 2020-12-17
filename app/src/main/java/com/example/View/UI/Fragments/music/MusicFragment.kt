
package com.example.View.UI.Fragments.music

import android.os.Build
import android.os.Bundle
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

class MusicFragment : Fragment(){
    private lateinit var musicViewModel: MusicViewModel
    private lateinit var songName: TextView
    private lateinit var songArtist: TextView
    private lateinit var songTime: TextView
    private lateinit var songDuration: TextView
    private lateinit var currentSongAlbum: ImageView


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
        var btnLoop: ImageView = root.findViewById(R.id.btnLoop)
        var btnSuffle: ImageView = root.findViewById(R.id.btnShuffle)
        var btnPlayPause: FloatingActionButton = root.findViewById(R.id.currentSongStatus)

        btnPlayPause.setOnClickListener {
            (activity as MainActivityView).btn_play_pauseClicked()
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

        var seekBar: SeekBar = root.findViewById(R.id.currentSongProgress)


        updateSelectedSong()
        return root
    }

    fun updateSelectedSong(){

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






}