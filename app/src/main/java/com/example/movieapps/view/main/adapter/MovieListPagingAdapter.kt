package com.example.movieapps.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
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
import com.example.movieapps.viewmodel.MainViewModel

class MovieListPagingAdapter:
    PagingDataAdapter<MainViewModel.UiMovieModel.ListMovie, RecyclerView.ViewHolder>(
    UIMODEL_COMPARATOR
){

    var itemWithMargin = 1

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
        holder.setIsRecyclable(false)
        /*val density: Float =  holder.itemView.context.resources.displayMetrics.scaledDensity
        val margin = (8 * density).toInt()
        if (position==itemWithMargin){
            var params:ViewGroup.MarginLayoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            params.rightMargin = margin
            params.leftMargin = margin
            itemWithMargin+=2
        }
        */
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
                    listener?.onClick(data)
                }*/
                /*txtUserName.text = data.authorDetails?.username
                txtUserReview.text = data.content
                var avatar = ""+data.authorDetails?.avatarPath
                if (avatar.contains("https://")){
                    if (avatar.startsWith("/")){
                        avatar = avatar.removeRange(0,1)
                    }
                    Glide.with(binding.root)
                        .load(avatar)
                        .apply(
                            RequestOptions.placeholderOf(R.drawable.ic_loading)
                                .error(R.drawable.ic_error)
                        )
                        .into(imgUserReview)
                }else{
                    Glide.with(binding.root)
                        .load("https://image.tmdb.org/t/p/w500/$avatar")
                        .apply(
                            RequestOptions.placeholderOf(R.drawable.ic_loading)
                                .error(R.drawable.ic_error)
                        )
                        .into(imgUserReview)
                }*/
            }
        }
    }
}
