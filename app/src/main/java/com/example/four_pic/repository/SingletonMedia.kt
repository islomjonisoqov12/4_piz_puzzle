package com.example.four_pic.repository

import android.media.MediaPlayer

class SingletonMedia private constructor(){
    companion object{
        private var MEDIAPLAYER:MediaPlayer?=null
        fun getInstance():MediaPlayer{
            if (MEDIAPLAYER==null){
                MEDIAPLAYER = MediaPlayer()
            }
            return MEDIAPLAYER!!
        }
    }
}