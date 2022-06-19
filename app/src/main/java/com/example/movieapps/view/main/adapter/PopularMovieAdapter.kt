package com.example.movieapps.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieapps.R
import com.example.movieapps.core.domain.main.model.MoviePopularEntity
import com.example.movieapps.databinding.ItemMovieHomeBinding
import com.example.movieapps.util.ItemClickListener

class PopularMovieAdapter:RecyclerView.Adapter<PopularMovieAdapter.ViewHolder>() {



    val listData:ArrayList<MoviePopularEntity?> = ArrayList()

    fun setData(list:List<MoviePopularEntity?>){
        listData.clear()
        listData.addAll(list)
        notifyDataSetChanged()
    }


    var listener:ItemClickListener<MoviePopularEntity?>? = null
    fun setPopularMovieCallback(itemClickListener: ItemClickListener<MoviePopularEntity?>?){
        listener = itemClickListener
    }



    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieAdapter.ViewHolder {
        return ViewHolder(ItemMovieHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularMovieAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = listData.size

    inner class ViewHolder(private val binding:ItemMovieHomeBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data:MoviePopularEntity?){
            binding.run {
                txtTitle.text = data?.title
                Glide.with(binding.root)
                    .load("https://image.tmdb.org/t/p/w500/"+data?.posterPath)
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