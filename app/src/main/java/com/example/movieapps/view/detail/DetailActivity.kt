package com.example.movieapps.view.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieapps.R
import com.example.movieapps.databinding.ActivityDetailBinding
import com.example.movieapps.databinding.ActivityMainBinding
import com.example.movieapps.view.detail.adapter.ReviewAdapter
import com.example.movieapps.view.review.ReviewActivity
import com.example.movieapps.view.trailer.TrailerActivity
import com.example.movieapps.viewmodel.MainViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }

    companion object{
        var ARG_MOVIE_ID = "arg_movie_id"
    }

    private val viewModel: MainViewModel by viewModel()

    var movieId = 0
    val apiKey = "a5956c69becb1fc204b4a2c79a865824"

    val reviewAdapter = ReviewAdapter()

    var trailerCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        movieId = intent.getIntExtra(ARG_MOVIE_ID,0)

        if (movieId!=0){
            viewModel.getMovieDetail(movieId.toString(),apiKey)
            viewModel.getMovieReview(movieId.toString(),apiKey)
            viewModel.getMovieTrailer(movieId.toString(),apiKey)
        }

        with(binding.rvReview){
            layoutManager = LinearLayoutManager(context, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = reviewAdapter
        }

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        setViewModelAction()

    }

    private fun setViewModelAction() {
        viewModel.dataMovieReview.observe(this){data->
            if (!data.isNullOrEmpty()){
                reviewAdapter.setData(data)
                binding.txtViewMoreReview.visibility = View.VISIBLE
                setViewAllReview()
            }else{
                binding.rvReview.visibility = View.GONE
                binding.incReviewEmpty.lytReviewEmpty.visibility = View.VISIBLE
            }
        }

        viewModel.dataMovieDetail.observe(this){data->
            if (data!=null){
                binding.run {
                    Glide.with(binding.root)
                        .load("https://image.tmdb.org/t/p/w500/"+data.posterPath)
                        .into(imgPoster)

                    Glide.with(binding.root)
                        .load("https://image.tmdb.org/t/p/w500/"+data.posterPath)
                        .apply(
                            RequestOptions.placeholderOf(R.drawable.ic_loading)
                                .error(R.drawable.ic_error)
                        )
                        .into(imgImage)

                    Glide.with(binding.root)
                        .load("https://image.tmdb.org/t/p/w500/"+data.backdropPath)
                        .apply(
                            RequestOptions.placeholderOf(R.drawable.ic_loading)
                                .error(R.drawable.ic_error)
                        )
                        .into(imgTrailer)

                    txtTitle.text = data.title
                    txtContent.text = data.overView
                    txtGenre.text = "Genre : ${data.genre}"

                    binding.shimmerFrameLayout.visibility = View.GONE
                    binding.lytData.visibility=View.VISIBLE

                }
            }
        }

        viewModel.dataMovieTrailer.observe(this){data->
            if (!data.isNullOrEmpty()){
                for(item in data){
                    if (item?.site!=null && item.key!=null){
                        val site:String = item.site?.lowercase()!!
                        if (site.contains("YouTube".lowercase())){
                            trailerCode = item.key!!
                            setTrailer()
                        }
                        break
                    }
                }
            }
        }

    }

    private fun setTrailer() {
        binding.imgPlay.isVisible = true
        binding.crdTrailer.isClickable = true
        lifecycle.addObserver(binding.youtubePlayerView)

        binding.crdTrailer.setOnClickListener {
            if (trailerCode!=""){
                binding.youtubePlayerView.visibility = View.VISIBLE
                binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(trailerCode, 0f)
                    }
                })
            }else{
                Toast.makeText(this@DetailActivity, "Trailer Tidak Ditemukan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setViewAllReview() {
        binding.txtViewMoreReview.setOnClickListener {
            val intent = Intent(this,ReviewActivity::class.java)
            intent.putExtra(ReviewActivity.ARG_MOVIE_ID,movieId)
            startActivity(intent)
        }
    }
}