package com.example.movieapps.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapps.R
import com.example.movieapps.databinding.ActivityMainBinding
import com.example.movieapps.view.main.adapter.ComingSoonAdapter
import com.example.movieapps.view.main.adapter.GenreAdapter
import com.example.movieapps.view.main.adapter.PopularMovieAdapter
import com.example.movieapps.view.main.adapter.TopRatedMovieAdapter
import com.example.movieapps.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by viewModel()


    val apiKey = "a5956c69becb1fc204b4a2c79a865824"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }




}