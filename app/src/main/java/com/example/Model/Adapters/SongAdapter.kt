package com.example.Model.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Model.Entities.PlayerList
import com.example.View.UI.MainActivityView
import com.example.idnpv001.R
import kotlinx.android.synthetic.main.music_list_item.view.*


class SongAdapter(private val songs: PlayerList) : RecyclerView.Adapter<SongAdapter.SongHolder>() {

    class SongHolder(v: View): RecyclerView.ViewHolder(v), View.OnClickListener{
        var view: View = v
        var songImage: ImageView? = null
        var songName: TextView? = null
        var songArtist: TextView? = null
        var songDuration: TextView? = null
        var context: Context? = v.context


        init{
            songImage = view.LVmusic_item_song_album
            songName = view.LVmusic_item_song_name
            songArtist = view.LVmusic_item_song_artist
            songDuration = view.LVmusic_item_song_duration
            v.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongAdapter.SongHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.music_list_item, parent, false)
        return SongHolder(view)
    }



    override fun onBindViewHolder(holder: SongAdapter.SongHolder, position: Int) {
        val song = songs.getPlaylist().get(position)

        var image = song.getAlbumArt(song.fileIconPath!!)
        if (image != null) {
            Glide.with(holder.view.context).asBitmap().load(image).into(holder.songImage!!)
        } else {
            Glide.with(holder.view.context).asBitmap().load(R.drawable.ic_musical_notes)
                .into(holder.songImage!!)
        }

        holder.songName?.text = song.mSongName

        holder.songArtist?.text = song.mSongArtist
        holder.songDuration?.text = song.mSongDuration

        holder.itemView.setOnClickListener {
            songs.selectList(position)
            (holder.view.context as MainActivityView).playlist_itemClicked(songs.getSongSelected().mFilePath)
        }

    }


    override fun getItemCount() = songs.getPlaylist().size


}