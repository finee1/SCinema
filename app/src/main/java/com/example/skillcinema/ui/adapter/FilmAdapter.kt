package com.example.skillcinema.ui.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FilmPreviewBinding
import com.example.skillcinema.entity.Film
import com.example.skillcinema.entity.Premier

class FilmAdapter(
    private val checkWatched: (Int) -> Boolean
) : PagingDataAdapter<Premier, MyNewViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNewViewHolder {
        val binding = FilmPreviewBinding.inflate(LayoutInflater.from(parent.context))
        return MyNewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyNewViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {
            if (item is Film) {
                if (item.ratingKinopoisk == null) {
                    rating.visibility = View.INVISIBLE
                } else {
                    rating.text = item.ratingKinopoisk.toString()
                }
                nameFilm.text = item.nameRu ?: item.nameOriginal
            } else {
                rating.visibility = View.INVISIBLE
                nameFilm.text = item?.nameRu ?: item?.nameEn
            }
            if (checkWatched(item?.kinopoiskId!!)){
                isWatched.visibility = View.VISIBLE
            }

            if (item?.genres != null && item.genres!!.isNotEmpty()) {
                    genre.text = if (item.genres?.size!! > 1) {
                        item.genres?.joinToString { it.genre.toString() }
                    } else item.genres!![0].genre
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
                it.findNavController().navigate(R.id.action_fullListFilmsFragment_to_filmFragment, bundle)
            }
        }

    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<Premier>() {
    override fun areItemsTheSame(oldItem: Premier, newItem: Premier): Boolean =
        oldItem.kinopoiskId == newItem.kinopoiskId

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Premier, newItem: Premier): Boolean =
        oldItem == newItem

}
