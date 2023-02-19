package com.ahmetaksoy.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    var songList:ArrayList<Songs> =ArrayList<Songs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layla = Songs(R.drawable.laylapic,"Layla","Eric Clapton",R.raw.layla)
        val ebh = Songs(R.drawable.rempic,"Everybody Hurts","R.E.M",R.raw.ebh)
        val yday = Songs(R.drawable.beatles,"Yesterday","The Beatles",R.raw.yday)
        val metallica = Songs(R.drawable.metallica,"Nothing Else Matter","Metallica",R.raw.nem)
        val cNumb = Songs(R.drawable.pfloyd,"Comfortably Numb","Pink Floyd",R.raw.cnumb)
        val ledZeppelin = Songs(R.drawable.lz,"Stairway To Heaven","Led Zeppelin",R.raw.sth)

        songList.add(layla)
        songList.add(ebh)
        songList.add(yday)
        songList.add(metallica)
        songList.add(cNumb)
        songList.add(ledZeppelin)

        ////////////
        val layoutManager = LinearLayoutManager(this)
        recyclerView=findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        val adapter =Adapter( object : Adapter.OnItemClickListener{
            override fun onItemClicked(position : Int){


                changeSong(position)


            }
        },songList )
        recyclerView.adapter = adapter






    }

    fun changeSong(pos:Int){
        //son eleman gelince sarkı calımıyor cunku fragmenti kapatıyoruz. ama aynı fragmenti donduruyorum bu if bloguyola. sonuc olarak çalışıyor
        if (pos==songList.size){
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            val fragment = SongDetail()
            val bundle = Bundle()

            bundle.putSerializable("sarki", songList[pos-1])
            bundle.putInt("sarki2", pos-1)


            fragment.arguments = bundle

            fragmentTransaction.replace(R.id.FrameLayoutid, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }


        if(pos<songList.size&&pos>=0){


        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val fragment = SongDetail()
        val bundle = Bundle()



            bundle.putSerializable("sarki", songList[pos])
            bundle.putInt("sarki2", pos)



        fragment.arguments = bundle

        fragmentTransaction.replace(R.id.FrameLayoutid, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    }


}