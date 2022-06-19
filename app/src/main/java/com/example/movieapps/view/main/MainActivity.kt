package com.example.movieapps.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.movieapps.databinding.ActivityMainBinding
import com.example.movieapps.view.main.adapter.ComingSoonAdapter
import com.example.movieapps.view.main.adapter.PopularMovieAdapter
import com.example.movieapps.view.main.adapter.TopRatedMovieAdapter
import com.example.movieapps.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }


}