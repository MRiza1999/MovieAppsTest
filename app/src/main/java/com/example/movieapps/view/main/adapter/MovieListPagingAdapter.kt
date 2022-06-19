package com.example.movieapps.view.main.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieapps.R
import com.example.movieapps.core.data.main.source.remote.response.ResultsItem
import com.example.movieapps.core.data.main.source.remote.response.ResultsItemReview
import com.example.movieapps.databinding.ItemMovieListBinding
import com.example.movieapps.databinding.ItemReviewUserBinding
import com.example.movieapps.util.ItemClickListener
import com.example.movieapps.view.detail.DetailActivity
import com.example.movieapps.viewmodel.MainViewModel

class MovieListPagingAdapter:
    PagingDataAdapter<MainViewModel.UiMovieModel.ListMovie, RecyclerView.ViewHolder>(
    UIMODEL_COMPARATOR
){

    var itemWithMargin = 1

    var listener:ItemClickListener<ResultsItem?>? = null

    fun setItemCallback(itemClickListener: ItemClickListener<ResultsItem?>?){
        listener = itemClickListener
    }

    companion object{
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<MainViewModel.UiMovieModel.ListMovie>(){
            override fun areItemsTheSame(oldItem: MainViewModel.UiMovieModel.ListMovie, newItem: MainViewModel.UiMovieModel.ListMovie): Boolean {
                return (oldItem is MainViewModel.UiMovieModel.ListMovie && newItem is MainViewModel.UiMovieModel.ListMovie && oldItem.dataListMovie.id == newItem.dataListMovie.id && oldItem.dataListMovie.id == newItem.dataListMovie.id)
            }

            override fun areContentsTheSame(oldItem: MainViewModel.UiMovieModel.ListMovie, newItem: MainViewModel.UiMovieModel.ListMovie): Boolean = oldItem==newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = getItem(position)
        (holder as ViewHolder).bind(uiModel?.dataListMovie!!)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,DetailActivity::class.java)
            intent.putExtra(DetailActivity.ARG_MOVIE_ID,uiModel.dataListMovie.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(ItemMovieListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    inner class ViewHolder(private val binding: ItemMovieListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ResultsItem) {
            binding.run {
                txtTitle.text = data.title
                Glide.with(binding.root)
                    .load("https://image.tmdb.org/t/p/w500/"+data.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgImage)

                /*binding.lytContainer.setOnClickListener {
                    Log.d("ClikListenerItemFilm","ini film ${data.title}")
                    listener?.onClick(data)
                }
*/
                /*binding.crdImage.setOnClickListener {
                    val context = binding.root.context
                    val intent = Intent(context,DetailActivity::class.java)
                    intent.putExtra("arg_movie_id",data.id)
                    context.startActivity(intent)
                }*/
            }
        }
    }
}
