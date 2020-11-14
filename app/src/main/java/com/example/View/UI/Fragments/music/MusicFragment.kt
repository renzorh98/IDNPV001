
package com.example.View.UI.Fragments.music

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ViewModel.MusicViewModel
import com.example.idnpv001.R
import kotlinx.android.synthetic.main.fragment_music.*

class MusicFragment : Fragment() {

    private lateinit var musicViewModel: MusicViewModel

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        musicViewModel =
            ViewModelProvider(this).get(MusicViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_music, container, false)

        val goToList:ImageView = root.findViewById(R.id.musicList)

        goToList.setOnClickListener{
            findNavController().navigate(R.id.navigation_music_list)
        }

        /*
        val avdImage: ImageView = root.findViewById(R.id.avdImage)
        var isChecked = true
        avdImage.setOnClickListener{
            if (isChecked){
                playToPause()
            }
            else{
                pauseToPlay()
            }

            isChecked = !isChecked
        }
        */


        return root
    }

    /*
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun pauseToPlay() {
        avdImage.setImageResource(R.drawable.pause_to_play)
        val avdPlayToPause: AnimatedVectorDrawable = avdImage.drawable as AnimatedVectorDrawable
        avdPlayToPause.start()
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun playToPause() {
        avdImage.setImageResource(R.drawable.play_to_pause)
        val avdPlayToPause: AnimatedVectorDrawable = avdImage.drawable as AnimatedVectorDrawable
        avdPlayToPause.start()
    }
    */

}