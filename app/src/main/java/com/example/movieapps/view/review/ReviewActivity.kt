package com.example.movieapps.view.review

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapps.R
import com.example.movieapps.databinding.ActivityReviewBinding
import com.example.movieapps.view.detail.DetailActivity
import com.example.movieapps.view.review.adapter.ReposLoadStateAdapter
import com.example.movieapps.view.review.adapter.ReviewPagingAdapter
import com.example.movieapps.viewmodel.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.viewmodel.ext.android.viewModel

class ReviewActivity : AppCompatActivity() {


    private val binding by lazy { ActivityReviewBinding.inflate(layoutInflater) }

    companion object{
        var ARG_MOVIE_ID = "arg_movie_id"
    }

    private val viewModel: MainViewModel by viewModel()

    var movieId = 0
    val apiKey = "a5956c69becb1fc204b4a2c79a865824"


    private var showJob: Job? = null

    var adapter:ReviewPagingAdapter? = ReviewPagingAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvItem.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvItem.adapter = adapter
        binding.retryButton.setOnClickListener {
            adapter?.retry()
        }
        lifecycleScope.launch {
            adapter?.loadStateFlow
                ?.distinctUntilChangedBy { it.refresh }
                ?.filter { it.refresh is LoadState.NotLoading }
                ?.collect { binding.rvItem.scrollToPosition(0) }
        }

        movieId = intent.getIntExtra(DetailActivity.ARG_MOVIE_ID,0)

        if (movieId!=0){
            initAdapter()
            getData()
        }
    }

    private fun getData(){
        showJob?.cancel()
        showJob = lifecycleScope.launch {
            viewModel.getMovieReviewPaging(movieId.toString(),apiKey)
                .collectLatest {
                    adapter?.submitData(it)
                    adapter?.notifyDataSetChanged()
                }
        }
    }


    private fun initAdapter(){
        binding.rvItem.adapter = adapter?.withLoadStateHeaderAndFooter(
            header = ReposLoadStateAdapter{adapter?.retry()},
            footer = ReposLoadStateAdapter{adapter?.retry()}
        )

        adapter?.addLoadStateListener { loadState->
            val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter?.itemCount == 0
            if (loadState.refresh is LoadState.NotLoading){
                binding.pbLoading.visibility = View.GONE
            }
            binding.pbLoading.isVisible = loadState.refresh is LoadState.Loading
            binding.rvItem.isVisible = loadState.refresh is LoadState.NotLoading
            if (binding.rvItem.isVisible && binding.rvItem.adapter?.itemCount == 0){
                Log.d("isiAdapterMasuk","Masuk")
                //emptyImage(true)
            }
            if(binding.rvItem.isVisible == false){
            }
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    this,
                    "\uD83D\uDE28 Wooops, Koneksi bermasalah.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}