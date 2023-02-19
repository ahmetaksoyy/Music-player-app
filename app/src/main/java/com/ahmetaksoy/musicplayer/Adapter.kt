package com.ahmetaksoy.musicplayer

import android.content.Intent
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val listener: OnItemClickListener,val liste:ArrayList<Songs>):RecyclerView.Adapter<Adapter.SongsHolder>() {

    class SongsHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val songName:TextView = itemView.findViewById(R.id.songText)
        val singerName:TextView = itemView.findViewById(R.id.singerText)
        val songImage:ImageView = itemView.findViewById(R.id.songPic)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent,false)

        return SongsHolder(itemView)

    }

    override fun onBindViewHolder(holder: SongsHolder, position: Int) {

        holder.songName.text =liste[position].songName
        holder.singerName.text =liste[position].singerName
        holder.songImage.setImageResource(liste[position].image)

        holder.itemView.setOnClickListener{
            listener.onItemClicked(position)

        }


    }

    override fun getItemCount(): Int {
return liste.size
    }


    interface OnItemClickListener {
        fun onItemClicked(position: Int) {

        }
    }
}