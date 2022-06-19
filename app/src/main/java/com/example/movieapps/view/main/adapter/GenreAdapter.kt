package com.example.movieapps.view.main.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieapps.R
import com.example.movieapps.core.domain.main.model.GenreListEntity
import com.example.movieapps.core.domain.main.model.MoviePopularEntity
import com.example.movieapps.databinding.ItemListGenreBinding
import com.example.movieapps.databinding.ItemMovieHomeBinding

class GenreAdapter:RecyclerView.Adapter<GenreAdapter.ViewHolder>() {


    val listChoiceGenre:ArrayList<GenreListEntity?> = ArrayList()
    var listData:ArrayList<GenreListEntity?> = ArrayList()

    fun setData(data:List<GenreListEntity?>){
        listData.clear()
        listData.addAll(data)
        notifyDataSetChanged()
    }

    fun getListChoice():ArrayList<GenreListEntity?>{
        return listChoiceGenre
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreAdapter.ViewHolder {
        return ViewHolder(ItemListGenreBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: GenreAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int  = listData.size

    inner class ViewHolder(private val binding: ItemListGenreBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data: GenreListEntity?){
            binding.run {
                txtGenre.text = data?.name
                if (data in listChoiceGenre){
                    txtGenre.setBackgroundColor(Color.parseColor("#ffffff"));
                    txtGenre.setTextColor(Color.parseColor("#000000"))
                }else{
                    txtGenre.setBackgroundColor(Color.parseColor("#000000"));
                    txtGenre.setTextColor(Color.parseColor("#ffffff"))
                }

                txtGenre.setOnClickListener {
                    if (data in listChoiceGenre){
                        listChoiceGenre.remove(data)
                    }else{
                        listChoiceGenre.add(data)
                    }
                    notifyDataSetChanged()
                }
            }
        }
    }

}