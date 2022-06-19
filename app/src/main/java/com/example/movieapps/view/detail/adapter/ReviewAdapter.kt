package com.example.movieapps.view.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieapps.R
import com.example.movieapps.core.domain.main.model.MovieReviewEntity
import com.example.movieapps.databinding.ItemMovieHomeBinding
import com.example.movieapps.databinding.ItemReviewUserBinding

class ReviewAdapter:RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {



    val listData:ArrayList<MovieReviewEntity?> = ArrayList()

    fun setData(data:List<MovieReviewEntity?>){
        listData.clear()
        listData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ViewHolder {
        return ViewHolder(ItemReviewUserBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        var size = listData.size
        if (size>5){
            size = 5
        }
        return size
    }

    inner class ViewHolder(private val binding: ItemReviewUserBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: MovieReviewEntity?){
            binding.run {
                txtUserName.text = data?.userName
                txtUserReview.text = data?.content
                var avatar = data?.avatarPath.toString()
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