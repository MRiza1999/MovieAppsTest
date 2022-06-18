package com.example.movieapps.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.movieapps.R
import com.example.movieapps.databinding.ActivityMainBinding
import com.example.movieapps.view.main.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {


    val apiKey = "a5956c69becb1fc204b4a2c79a865824"

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.getGenreList(apiKey)

        setViewModelAction()

    }

    private fun setViewModelAction() {

        viewModel.dataGenreList.observe(this){data->
            if (data!=null && data.isNotEmpty()){
                binding.txtText.text = "Jumlah genre ${data.size}"
            }
        }

        viewModel.isErrorGenreList.observe(this){message->
            if (message!=null){
                Log.d("ErrorLog","Genre List Error $message")
            }
        }

    }
}