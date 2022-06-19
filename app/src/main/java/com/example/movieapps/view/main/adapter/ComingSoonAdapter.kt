package com.example.movieapps.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieapps.R
import com.example.movieapps.core.domain.main.model.MovieComingSoonEntity
import com.example.movieapps.core.domain.main.model.MovieTopRatedEntity
import com.example.movieapps.databinding.ItemHomeComingSoonBinding
import com.example.movieapps.util.ItemClickListener

class ComingSoonAdapter:RecyclerView.Adapter<ComingSoonAdapter.ViewHolder>() {

    val listData:ArrayList<MovieComingSoonEntity?> = ArrayList()

    fun setData(list:List<MovieComingSoonEntity?>){
        listData.clear()
        listData.addAll(list)
        notifyDataSetChanged()
    }

    var listener: ItemClickListener<MovieComingSoonEntity?>? = null
    fun setComingSoonMovieCallback(itemClickListener: ItemClickListener<MovieComingSoonEntity?>?){
        listener = itemClickListener
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComingSoonAdapter.ViewHolder {
        return ViewHolder(ItemHomeComingSoonBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ComingSoonAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        var size = listData.size
        if (size>5){
            size = 5
        }
        return size
    }

    inner class ViewHolder(private val binding: ItemHomeComingSoonBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data: MovieComingSoonEntity?){
            binding.run {
                Glide.with(binding.root)
                    .load("https://image.tmdb.org/t/p/w500/"+data?.backdropPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgImage)
                binding.lytContainer.setOnClickListener {
                    listener?.onClick(data)
                }
            }
        }
    }

}