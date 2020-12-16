package com.example.Model.Interface

interface IActionPlaying {
    fun btn_play_pauseClicked()
    fun btn_prevClicked(type: Boolean)
    fun btn_nextClicked(type: Boolean)
    fun playlist_itemClicked(_path: String)
}