package com.example.movieapps.view.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieapps.R
import com.example.movieapps.core.data.main.source.remote.response.ResultsItemReview
import com.example.movieapps.databinding.ItemReviewUserBinding
import com.example.movieapps.viewmodel.MainViewModel

class ReviewPagingAdapter:PagingDataAdapter<MainViewModel.UiReviewModel.DetailListReview, RecyclerView.ViewHolder>(
    UIMODEL_COMPARATOR
){
    companion object{
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<MainViewModel.UiReviewModel.DetailListReview>(){
            override fun areItemsTheSame(oldItem: MainViewModel.UiReviewModel.DetailListReview, newItem: MainViewModel.UiReviewModel.DetailListReview): Boolean {
                return (oldItem is MainViewModel.UiReviewModel.DetailListReview && newItem is MainViewModel.UiReviewModel.DetailListReview && oldItem.dataListReview.id == newItem.dataListReview.id && oldItem.dataListReview.id == newItem.dataListReview.id)
            }

            override fun areContentsTheSame(oldItem: MainViewModel.UiReviewModel.DetailListReview, newItem: MainViewModel.UiReviewModel.DetailListReview): Boolean = oldItem==newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = getItem(position)
        (holder as ViewHolder).bind(uiModel?.dataListReview!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(ItemReviewUserBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    inner class ViewHolder(private val binding: ItemReviewUserBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ResultsItemReview) {
            binding.run {
                txtUserName.text = data.authorDetails?.username
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
                }
            }
        }
    }
}
