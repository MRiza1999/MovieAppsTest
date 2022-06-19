package com.example.movieapps.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieapps.R
import com.example.movieapps.core.domain.main.model.MovieTopRatedEntity
import com.example.movieapps.databinding.ItemMovieHomeBinding

class TopRatedMovieAdapter:RecyclerView.Adapter <TopRatedMovieAdapter.ViewHolder>() {


    val listData:ArrayList<MovieTopRatedEntity?> = ArrayList()

    fun setData(list:List<MovieTopRatedEntity?>){
        listData.clear()
        listData.addAll(list)
        notifyDataSetChanged()
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedMovieAdapter.ViewHolder {
        return ViewHolder(ItemMovieHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: TopRatedMovieAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = listData.size

    inner class ViewHolder(private val binding: ItemMovieHomeBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: MovieTopRatedEntity?){
            binding.run {
                txtTitle.text = data?.title
                Glide.with(binding.root)
                    .load("https://image.tmdb.org/t/p/w500/"+data?.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgImage)
            }
        }
    }

}