package com.example.movieapps.view.trailer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapps.databinding.ActivityMainBinding
import com.example.movieapps.databinding.ActivityTrailerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class TrailerActivity : AppCompatActivity() {

    companion object{
        var ARG_VIDEO_ID = "arg_video_id"
    }


    val binding by lazy { ActivityTrailerBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val videoId = intent.getStringExtra(ARG_VIDEO_ID)

        Log.d("VideoIDNow",videoId.toString())


        if (videoId!=null){

        }

    }
}