package com.ahmetaksoy.musicplayer

import java.io.Serializable


data class Songs(val image:Int,val songName:String,val singerName:String,val song: Int) : Serializable {
}