package com.example.movieapps.view.main.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapps.R
import com.example.movieapps.databinding.FragmentGenreBinding
import com.example.movieapps.databinding.FragmentHomeBinding
import com.example.movieapps.view.detail.DetailActivity
import com.example.movieapps.view.main.adapter.MovieListPagingAdapter
import com.example.movieapps.view.review.adapter.ReposLoadStateAdapter
import com.example.movieapps.view.review.adapter.ReviewPagingAdapter
import com.example.movieapps.viewmodel.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class GenreFragment : Fragment() {


    private lateinit var binding: FragmentGenreBinding

    val apiKey = "a5956c69becb1fc204b4a2c79a865824"

    private var showJob: Job? = null
    var genre = "28"

    var adapter: MovieListPagingAdapter? = MovieListPagingAdapter()

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvItem.layoutManager = GridLayoutManager(context, 3)
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
        getData()
        initAdapter()
    }


    private fun getData(){
        showJob?.cancel()
        showJob = lifecycleScope.launch {
            viewModel.getMovieListPaging(apiKey,genre)
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
                    context,
                    "\uD83D\uDE28 Wooops, Koneksi bermasalah.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}