package com.example.movieapps.view.main.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapps.core.domain.main.model.MovieComingSoonEntity
import com.example.movieapps.core.domain.main.model.MoviePopularEntity
import com.example.movieapps.core.domain.main.model.MovieTopRatedEntity
import com.example.movieapps.databinding.FragmentHomeBinding
import com.example.movieapps.util.ItemClickListener
import com.example.movieapps.view.detail.DetailActivity
import com.example.movieapps.view.main.MainActivity
import com.example.movieapps.view.main.adapter.ComingSoonAdapter
import com.example.movieapps.view.main.adapter.PopularMovieAdapter
import com.example.movieapps.view.main.adapter.TopRatedMovieAdapter
import com.example.movieapps.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragmentFragment : Fragment() {

    //val apiKey = "a5956c69becb1fc204b4a2c79a865824"
    val handler = Handler(Looper.getMainLooper())

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: MainViewModel by viewModel()
    val popularAdapter = PopularMovieAdapter()
    val comingSoonAdapter = ComingSoonAdapter()
    val topRatedAdapter = TopRatedMovieAdapter()

    lateinit var activity: MainActivity

    var isMoviePopularLoading = false
    var isMovieTopRatedLoading = false
    var isMovieComingSoonLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity = getActivity() as MainActivity

        with(binding.rvPopular){
            layoutManager = LinearLayoutManager(context, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = popularAdapter
        }

        with(binding.rvTop){
            layoutManager = LinearLayoutManager(context, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = topRatedAdapter
        }

        //viewModel.getGenreList(activity.apiKey)
        viewModel.getMoviePopular(activity.apiKey)
        viewModel.getMovieComingSoon(activity.apiKey)
        viewModel.getMovieTopRated(activity.apiKey)
        setViewModelAction()
    }


    private fun setViewModelAction() {
        viewModel.isLoadingMoviePopular.observe(viewLifecycleOwner){status->
            isMoviePopularLoading = status
            setView()
        }

        viewModel.isLoadingMovieTopRated.observe(viewLifecycleOwner){status->
            isMovieTopRatedLoading = status
            setView()
        }

        viewModel.isLoadingMovieComingSoon.observe(viewLifecycleOwner){status->
            isMovieComingSoonLoading = status
            setView()
        }

        viewModel.dataMoviePopular.observe(viewLifecycleOwner){data->
            if (!data.isNullOrEmpty()){
                popularAdapter.setData(data)
                setMoviePopularCallback()
            }
        }

        viewModel.dataMovieComingSoon.observe(viewLifecycleOwner){data->
            if (!data.isNullOrEmpty()){
                comingSoonAdapter.setData(data)
                setComingSoon()
                setMovieComingSoonCallback()
            }
        }

        viewModel.dataMovieTopRated.observe(viewLifecycleOwner){data->
            if (!data.isNullOrEmpty()){
                topRatedAdapter.setData(data)
                setMovieTopRatedCallback()
            }
        }



    }

    private fun setView() {
        if (!isMovieComingSoonLoading && !isMoviePopularLoading && !isMovieTopRatedLoading){
            binding.lytData.visibility = View.VISIBLE
            binding.shimmerFrameLayout.visibility = View.GONE
        }
    }

    private fun setMovieTopRatedCallback() {
        topRatedAdapter.setTopRatedMovieCallback(object :ItemClickListener<MovieTopRatedEntity?>{
            override fun onClick(data: MovieTopRatedEntity?) {
                val intent = Intent(context,DetailActivity::class.java)
                intent.putExtra(DetailActivity.ARG_MOVIE_ID,data?.id)
                startActivity(intent)
            }
        })
    }

    private fun setMovieComingSoonCallback() {
        comingSoonAdapter.setComingSoonMovieCallback(object :ItemClickListener<MovieComingSoonEntity?>{
            override fun onClick(data: MovieComingSoonEntity?) {
                val intent = Intent(context,DetailActivity::class.java)
                intent.putExtra(DetailActivity.ARG_MOVIE_ID,data?.id)
                startActivity(intent)
            }
        })
    }

    private fun setMoviePopularCallback() {
        popularAdapter.setPopularMovieCallback(object :ItemClickListener<MoviePopularEntity?>{
            override fun onClick(data: MoviePopularEntity?) {
                val intent = Intent(context,DetailActivity::class.java)
                intent.putExtra(DetailActivity.ARG_MOVIE_ID,data?.id)
                startActivity(intent)
            }
        })
    }

    private fun setComingSoon() {
        binding.incComingSoon.viewPagerComingSoon.adapter = comingSoonAdapter
        binding.incComingSoon.viewPagerComingSoon.clipToPadding = false
        binding.incComingSoon.viewPagerComingSoon.clipChildren = false
        binding.incComingSoon.viewPagerComingSoon.offscreenPageLimit = 3
        binding.incComingSoon.viewPagerComingSoon.getChildAt(0)!!.overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        var compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(10))
        compositePageTransformer.addTransformer(object : ViewPager2.PageTransformer{
            override fun transformPage(page: View, position: Float) {
                val r = 1-Math.abs(position)
                page.scaleY = 0.85f+r*0.15f
            }
        })

        binding.incComingSoon.viewPagerComingSoon.setPageTransformer(compositePageTransformer)

        binding.incComingSoon.viewPagerComingSoon.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                handler.removeCallbacks(sliderRunable)
                handler.postDelayed(sliderRunable,3000)
            }
        })

        binding.incComingSoon.dotsIndicatorComingSoon.setViewPager2(binding.incComingSoon.viewPagerComingSoon)

    }

    val sliderRunable: Runnable = object : Runnable{
        override fun run() {
            if (binding.incComingSoon.viewPagerComingSoon.currentItem==4){
                binding.incComingSoon.viewPagerComingSoon.setCurrentItem(0)
            }else{
                binding.incComingSoon.viewPagerComingSoon.setCurrentItem(binding.incComingSoon.viewPagerComingSoon.currentItem + 1)
            }
        }

    }
}