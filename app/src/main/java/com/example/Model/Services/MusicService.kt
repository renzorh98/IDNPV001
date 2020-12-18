package com.example.Model.Services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MusicService(): Service() {
    inner class MyBinder() : Binder() {
        fun getService(): MusicService{
            return this@MusicService
        }
    }

    var mBinder: IBinder = MyBinder()
    lateinit var mediaPlayer: MediaPlayer
    private var isOnPause = 0
    private var isOnStop = 0

    override fun onCreate() {
        super.onCreate()
        Log.e("asd","HIIIIIIIIIIIIIIIIIIIIIIIIIIIII")
        iniMediaPlayer()
    }


    override fun onBind(p0: Intent?): IBinder? {
        return mBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    fun start(){
        mediaPlayer.start()
        setPause(0)
        setStop(0)
    }
    fun pause(){
        mediaPlayer.pause()
        setPause(1)
    }
    fun isPlaying(): Boolean{
        return mediaPlayer.isPlaying()
    }
    fun stop(){
        mediaPlayer.stop()
        setStop(1)
    }
    fun release(){
        mediaPlayer.release()
    }
    fun getDuration(): Int{
        return mediaPlayer.duration
    }
    fun seekTo(position: Int){
        mediaPlayer.seekTo(position)
    }
    fun iniMediaPlayer(){
        mediaPlayer = MediaPlayer()
    }
    fun createMediaPlayer(path: String){
        var uri = Uri.parse(path)
        if(isPlaying()){
            stop()
            release()
            mediaPlayer = MediaPlayer.create(baseContext,uri)
            resetPauseValue()
            start()
        }
        else{
            mediaPlayer = MediaPlayer.create(baseContext,uri)
            resetPauseValue()
        }

        //mediaPlayer.setDataSource(path)

    }

    fun setDataSource(path: String){
        mediaPlayer.setDataSource(path)
        mediaPlayer.prepare()
    }

    fun setPause(_pause_play: Int){
        this.isOnPause = _pause_play
    }
    fun setStop(_pause_play: Int){
        this.isOnStop = _pause_play
    }

    fun isOnPause(): Boolean{
        return (isOnPause == 1)
    }

    fun isOnStop(): Boolean{
        return (isOnStop == 1)
    }


    private fun resetPauseValue(){
        this.isOnPause = 0
    }
    fun getCurrentPosition(): Int{
        return mediaPlayer.currentPosition
    }



}