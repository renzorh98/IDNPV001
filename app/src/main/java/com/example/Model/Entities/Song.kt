package com.example.Model.Entities

import android.media.MediaMetadataRetriever
import java.io.FileInputStream
import java.io.Serializable
import java.util.concurrent.TimeUnit

class Song(
    val fileIconPath: String,
    val name: String,
    val artist: String,
    val duration: String
): Serializable{
    var mFilePath = fileIconPath;
    var mSongName = name;
    var mSongArtist = artist;
    var mSongDuration = transformTimes(duration.toLong());

    private fun transformTimes(millis: Long):String{
        var duration = String.format("%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(millis),
            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))

        return duration
    }

    fun getAlbumArt(path: String): ByteArray? {
        var retriever = MediaMetadataRetriever()
        var inputStream = FileInputStream(path)
        retriever.setDataSource(inputStream.fd)
        var art = retriever.getEmbeddedPicture();
        retriever.release()
        return art

    }
}