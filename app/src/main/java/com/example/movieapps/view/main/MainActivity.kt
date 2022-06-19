package com.example.movieapps.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapps.R
import com.example.movieapps.databinding.ActivityMainBinding
import com.example.movieapps.view.main.adapter.ComingSoonAdapter
import com.example.movieapps.view.main.adapter.GenreAdapter
import com.example.movieapps.view.main.adapter.PopularMovieAdapter
import com.example.movieapps.view.main.adapter.TopRatedMovieAdapter
import com.example.movieapps.view.main.fragment.GenreFragment
import com.example.movieapps.view.main.fragment.HomeFragmentFragment
import com.example.movieapps.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by viewModel()


    var homeFragment = HomeFragmentFragment()
    var genreFragment = GenreFragment()
    var active: Fragment = homeFragment


    var ft: FragmentTransaction = supportFragmentManager.beginTransaction()
    val apiKey = "a5956c69becb1fc204b4a2c79a865824"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        initListener()
        binding.lytFilter.visibility = View.INVISIBLE
    }

    private fun initListener() {
        binding.lytBottomHome.setOnClickListener {
            supportFragmentManager.beginTransaction().hide(active).show(homeFragment)
                .commit()
            active = homeFragment
            navigateWithoutAnim(0)
        }
        binding.lytBottomGenre.setOnClickListener {
            supportFragmentManager.beginTransaction().hide(active).show(genreFragment)
                .commit()
            active = genreFragment
            navigateWithoutAnim(1)
        }
    }

    override fun onResume() {
        super.onResume()
        if (!genreFragment.isAdded){
            ft.add(R.id.fragment_container,genreFragment,"2").hide(genreFragment)
        }
        if (!homeFragment.isAdded){
            ft.add(R.id.fragment_container,homeFragment,"1").commit()
        }
    }



    fun navigateWithoutAnim(index: Int) {
        //val controller = Navigation.findNavController(this, R.id.fragment_container)
        when (index) {
            0 -> {
                binding.lytFilter.visibility = View.INVISIBLE
                binding.txtPageTitle.text = resources.getString(R.string.home)
            }
            1 -> {
                binding.lytFilter.visibility = View.VISIBLE
                binding.txtPageTitle.text = resources.getString(R.string.genre)
            }
        }
    }

}