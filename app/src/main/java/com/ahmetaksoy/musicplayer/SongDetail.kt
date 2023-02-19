package com.ahmetaksoy.musicplayer

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback

class SongDetail : Fragment() {
    lateinit var runnable: Runnable
    private var handler= Handler()
    lateinit var mediaPlayer:MediaPlayer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view : View =inflater.inflate(R.layout.fragment_song_detail, container, false)


        val imageView:ImageView = view.findViewById(R.id.imageView)
        val songName: TextView = view.findViewById(R.id.textView)
        val singerName: TextView = view.findViewById(R.id.textView2)
        val btn :ImageView = view.findViewById(R.id.playButton)
        val seekbar:SeekBar = view.findViewById(R.id.seekBar)
        val btnNext :ImageView = view.findViewById(R.id.nextMusic)
        val btnPrev :ImageView = view.findViewById(R.id.prevMusic)
        val seekbarVol:SeekBar = view.findViewById(R.id.seekBar2)
        val baslangicTime: TextView = view.findViewById(R.id.textView3)
        val bitisTime: TextView = view.findViewById(R.id.textView4)


        val selectedGame = arguments?.getSerializable("sarki") as Songs
       val selectedGamePos = arguments?.getInt("sarki2")!!.toInt()
             imageView.setImageResource(selectedGame.image)
             songName.text=selectedGame.songName
             singerName.text = selectedGame.singerName
            //bu kod fragmentin arka planını beyazlaştırıyor ve sanki yeni bir sayfa açılmış gibi oluyor.
            view.setBackgroundColor(Color.parseColor("white") )
        view.isClickable=true
        view.isFocusable=true


        //bu mediaplayerı singleton ile yap cunku aynı anda sarkılar açılabiliyor.
        //bu problemi sarki degistiginde veya bittiginde mediaplayer ve fragmenti kapatarak cozdum
        val sarkii : Int = selectedGame.song


        mediaPlayer =  MediaPlayer.create(requireContext(),sarkii)
        seekbar.progress=0
        seekbarVol.progress=50

        mediaPlayer.setVolume(0.5f,0.5f)
        seekbar.max = mediaPlayer.duration
        var zaman = mediaPlayer.duration.toLong()

        var timeLabel = ""

        var min = zaman / 1000 / 60
        var sec = zaman / 1000 % 60
        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec


        bitisTime.text=timeLabel



        btn.setOnClickListener{
            if (!mediaPlayer.isPlaying){
                mediaPlayer.start()
                btn.setImageResource(R.drawable.pause)
            }
            else{
                   mediaPlayer.pause()
                   btn.setImageResource(R.drawable.play)


            }
        }



        //mainActivityde tanımladıgım fonksiyonu kullanarak diger sarkıya geçiyoruz.
        val activity = requireActivity() as MainActivity






        btnNext.setOnClickListener{
            //bu butonla diger sarkıya geçtigi zaman bir onceki fragmenti kapatıyorum. cunku geri tusuna basınca her bir fragmenti tekrar geçiyordu. yani ustuste fragment açılıyordu galiba
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.remove(this).commit()

            mediaPlayer.reset()
                activity.changeSong(selectedGamePos + 1)

                  }

        btnPrev.setOnClickListener{

            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.remove(this).commit()

            mediaPlayer.reset()
            activity.changeSong(selectedGamePos-1)
        }



        seekbarVol.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, pos: Int, changed: Boolean) {
                if(changed){
                    var volLevel=pos/100.0f
                    mediaPlayer.setVolume(volLevel,volLevel)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })




        seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, pos: Int, changed: Boolean) {
                if (changed){
                    mediaPlayer.seekTo(pos)
                }


                }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        runnable= Runnable {
            seekbar.progress = mediaPlayer.currentPosition

            var timeLabel = ""

            var min = seekbar.progress / 1000 / 60
            var sec = seekbar.progress / 1000 % 60
            if (min<10) timeLabel+="0"
            timeLabel = "$min:"
            if (sec < 10) timeLabel += "0"
            timeLabel += sec

            baslangicTime.text=timeLabel

            handler.postDelayed(runnable,1000)

        }
        handler.postDelayed(runnable,1000)

        //sarki bitince basa donme
        mediaPlayer.setOnCompletionListener {
            btn.setImageResource(R.drawable.play)

            mediaPlayer.reset()
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.remove(this).commit()

            seekbar.progress=0
        }




        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //Geri tuşuna basıldığında yapılacak işlem
                mediaPlayer.reset()
                val fragmentManager = parentFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.remove(this@SongDetail).commit()

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
    }


