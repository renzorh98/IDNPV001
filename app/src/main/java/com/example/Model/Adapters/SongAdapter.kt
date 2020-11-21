package com.example.Model.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.Model.Entities.Song
import com.example.idnpv001.R
import kotlinx.android.synthetic.main.music_list_item.view.*

class SongAdapter(private val songs: List<Song>) : RecyclerView.Adapter<SongAdapter.SongHolder>() {

    class SongHolder(v: View): RecyclerView.ViewHolder(v), View.OnClickListener{
        var view: View = v
        var songName: TextView? = null
        var songArtist: TextView? = null

        init {
            songName = view.music_item_song_name
            songArtist = view.music_item_song_artist
            v.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            //launch
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongAdapter.SongHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.music_list_item, parent, false)
        return SongHolder(view)
    }

    override fun onBindViewHolder(holder: SongAdapter.SongHolder, position: Int) {
        val song = songs.get(position)
        holder.songName?.text = song.name
        holder.songArtist?.text = song.artist

    }

    override fun getItemCount() = songs.size


}