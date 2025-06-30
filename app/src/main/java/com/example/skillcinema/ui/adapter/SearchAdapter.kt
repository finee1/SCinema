package com.example.skillcinema.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FilmPrveviewSearchBinding
import com.example.skillcinema.entity.Film
import com.example.skillcinema.entity.Premier

class SearchAdapter(
    private val checkWatched: (Int) -> Boolean
) : PagingDataAdapter<Premier, SearchViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = FilmPrveviewSearchBinding.inflate(LayoutInflater.from(parent.context))
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        val parent = holder.itemView.drawingTime

        with(holder.binding) {
            if (item is Film) {
                if (item.ratingKinopoisk == null) {
                    rating.visibility = View.INVISIBLE
                } else {
                    rating.text = item.ratingKinopoisk.toString()
                }
                textNameSearch.text = item.nameRu ?: item.nameOriginal
            } else {
                rating.visibility = View.INVISIBLE
                textNameSearch.text = item?.nameRu ?: item?.nameEn
            }

            if (checkWatched(item?.kinopoiskId!!)){
                isWatched.visibility = View.VISIBLE
            }

            if (item?.genres != null && item.genres!!.isNotEmpty()) {
                yearGenreSearch.text = if (item.genres?.size!! > 1) {
                    "${item.year}, " + item.genres?.joinToString { it.genre.toString() }
                } else "${item.year}, " + item.genres!![0].genre
            }
            item.let {
                Glide.with(poster.context)
                    .load(it?.posterUrl)
                    .centerCrop()
                    .into(poster)
            }
            this.root.setOnClickListener {
                val bundle = Bundle().apply {
                    putInt("param1", item?.kinopoiskId ?: 1)
                }
                it.findNavController().navigate(R.id.action_navigation_dashboard_btn_to_filmFragment, bundle)
            }
        }

    }
}

class SearchViewHolder(val binding: FilmPrveviewSearchBinding) : RecyclerView.ViewHolder(binding.root)