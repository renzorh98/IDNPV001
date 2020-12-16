package com.example.Model.Entities

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import java.io.Serializable

class PlayerList(_context: Context): Serializable{
    private var songs: ArrayList<Song> = ArrayList()
    private lateinit var context: Context
    private var select: Int = 0
    //private var change: Boolean = true

    init {
        this.context = _context
        onStartCommand()
    }

    fun onStartCommand(){
        createPlayList()
    }

    private fun createPlayList(){
        songs = ArrayList()
        val resolver = context.contentResolver
        var songCursor: Cursor? = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,null,null,null)

        while(songCursor != null && songCursor.moveToNext()){
            var filePath = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA))
            var songName = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            var songArtist = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            var songDuration = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION))


            var aux = Song(
                filePath,
                songName,
                songArtist,
                songDuration
            )

            songs.add(aux)

        }
        songCursor!!.close()
    }

    fun getPlaylist(): ArrayList<Song>{
        return this.songs
    }

    fun getSize(): Int{
        return this.songs.size
    }
    fun updatePlayerList(){
        createPlayList()
    }
    fun selectList(position: Int){
        this.select = position
    }
    fun getSongSelected(): Song{
        return songs.get(this.select)
    }
    /*fun setChange(){
        change = !change
    }
    fun getChageState(): Boolean{
        return change
    }*/
    fun skipSelectList(){
        this.select = this.select+1
        if(this.select > getSize()-1){
            this.select = 0
        }
        Log.e("position: ", this.select.toString())
    }
    fun prevSelectList(){
        this.select = this.select-1
        if(this.select < 0){
            this.select = getSize()-1
        }
        Log.e("position: ", this.select.toString())
    }


}