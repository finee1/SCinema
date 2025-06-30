package com.example.skillcinema.ui.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FilmPreviewBinding
import com.example.skillcinema.entity.Premier

class PremierAdapter(
    private val checkWatched: (Int) -> Boolean
) : RecyclerView.Adapter<MyNewViewHolder>() {

    private var values: List<Premier> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Premier>) {
        this.values = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNewViewHolder {
        val binding = FilmPreviewBinding.inflate(LayoutInflater.from(parent.context))
        return MyNewViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyNewViewHolder, position: Int) {
        val item = values.getOrNull(position)

        with(holder.binding) {
            rating.visibility = View.INVISIBLE
            nameFilm.text = item?.nameRu ?: item?.nameEn
            genre.text = if (item?.genres?.size!! > 1) {
                item.genres?.joinToString { it.genre.toString() }
            } else item.genres!![0].genre
            item.let {
                Glide.with(poster.context)
                    .load(it.posterUrl)
                    .centerCrop()
                    .into(poster)
            }
            if (checkWatched(item.kinopoiskId!!)){
                isWatched.visibility = View.VISIBLE
            }
            this.root.setOnClickListener {
                val bundle = Bundle().apply {
                    putInt("param1", item.kinopoiskId ?: 1)
                }
                it.findNavController().navigate(R.id.action_fullListFilmsFragment_to_filmFragment, bundle)
            }
        }
    }

    override fun getItemCount(): Int = values.size
}
