package com.example.movieapps.view.main.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapps.R
import com.example.movieapps.core.data.main.source.remote.response.ResultsItem
import com.example.movieapps.databinding.FragmentGenreBinding
import com.example.movieapps.databinding.FragmentHomeBinding
import com.example.movieapps.util.ItemClickListener
import com.example.movieapps.util.Utils
import com.example.movieapps.view.detail.DetailActivity
import com.example.movieapps.view.main.MainActivity
import com.example.movieapps.view.main.adapter.GenreAdapter
import com.example.movieapps.view.main.adapter.MovieListPagingAdapter
import com.example.movieapps.view.review.adapter.ReposLoadStateAdapter
import com.example.movieapps.view.review.adapter.ReviewPagingAdapter
import com.example.movieapps.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class GenreFragment : Fragment() {


    lateinit var binding: FragmentGenreBinding
    private var showJob: Job? = null
    var genre = "28"

    var adapter: MovieListPagingAdapter? = MovieListPagingAdapter()
    private val viewModel: MainViewModel by viewModel()


    lateinit var bottomSheetFilter: ConstraintLayout
    var bottomSheetBehaviorFilter: BottomSheetBehavior<ConstraintLayout?>? = null
    var rvFilter: RecyclerView? = null

    lateinit var activity:MainActivity
    var genreAdapter = GenreAdapter()

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

        activity = getActivity() as MainActivity

        viewModel.getGenreList(activity.apiKey)

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
        activity.binding.lytFilter.setOnClickListener {
            if (bottomSheetBehaviorFilter?.state ==BottomSheetBehavior.STATE_COLLAPSED){
                bottomSheetBehaviorFilter?.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }


        setBottomSheetFilter()
        setViewModelAction()
        getData()
        initAdapter()
    }


    private fun setBottomSheetFilter() {
        bottomSheetFilter = binding.incBottomSheet.bottomsheetFilter
        rvFilter = binding.incBottomSheet.rvFilter
        val bottomSheet = BottomSheetBehavior.from(bottomSheetFilter)
        bottomSheet.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.bg.visibility = View.INVISIBLE
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.bg.visibility = View.VISIBLE
                binding.bg.alpha = slideOffset
            }
        })
        bottomSheetBehaviorFilter = bottomSheet

        with(rvFilter){
            this?.layoutManager = LinearLayoutManager(context, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
            this?.setHasFixedSize(true)
            this?.adapter = genreAdapter
        }


        binding.incBottomSheet.btnFilter.setOnClickListener {
            genre = Utils.genreListToString(genreAdapter.getListChoice())
            getData()
            bottomSheetBehaviorFilter?.state = BottomSheetBehavior.STATE_COLLAPSED
            Toast.makeText(context, genre, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setViewModelAction() {
        viewModel.dataGenreList.observe(viewLifecycleOwner){data->
            if (!data.isNullOrEmpty()){
                genreAdapter.setData(data)
            }
        }
    }

    private fun getData(){
        showJob?.cancel()
        showJob = lifecycleScope.launch {
            viewModel.getMovieListPaging(activity.apiKey,genre)
                .collectLatest {
                    adapter?.submitData(it)
                    adapter?.notifyDataSetChanged()
                    //setCallback()
                }
        }
    }

   /* private fun setCallback() {
        adapter?.setItemCallback(object:ItemClickListener<ResultsItem?>{
            override fun onClick(data: ResultsItem?) {
                val intent = Intent(context,DetailActivity::class.java)
                intent.putExtra(DetailActivity.ARG_MOVIE_ID,data?.id)
                startActivity(intent)
            }
        })
    }*/


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