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
import com.example.skillcinema.databinding.RightArrowBinding
import com.example.skillcinema.entity.Film
import com.example.skillcinema.entity.Premier

class FilmPreviewAdapter(
    private val key: String,
    private val checkWatched: (Int) -> Boolean
    ) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {
        const val ITEM_COUNT = 20
        const val SHOW_ALL_BTN_COUNT = 1
    }

    private var values: List<Premier> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Premier>) {
        this.values = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.film_preview -> {
                val binding = FilmPreviewBinding.inflate(LayoutInflater.from(parent.context))
                MyNewViewHolder(binding)
            }
            R.layout.right_arrow -> {
                val binding = RightArrowBinding.inflate(LayoutInflater.from(parent.context))
                MySecondViewHolder(binding)
            }
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (values.isNotEmpty()) {
            when (getItemViewType(position)) {
                R.layout.film_preview -> {
                    val item = values.getOrNull(position)
                    val binding = (holder as MyNewViewHolder).binding
                    with(binding) {
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

                        this.root.setOnClickListener {
                            val bundle = Bundle().apply {
                                putInt("param1", item?.kinopoiskId ?: 1)
                            }
                            it.findNavController()
                                .navigate(R.id.action_navigation_home_btn_to_filmFragment, bundle)
                        }

                        if (checkWatched(item?.kinopoiskId!!)){
                            isWatched.visibility = View.VISIBLE
                        }

                        genre.text = if (item?.genres?.size!! > 1) {
                            item.genres?.joinToString { it.genre.toString() }
                        } else item.genres!![0].genre
                        item.let {
                            Glide.with(poster.context)
                                .load(it.posterUrl)
                                .centerCrop()
                                .into(poster)
                        }
                    }
                }
                R.layout.right_arrow -> {
                    val binding = (holder as MySecondViewHolder).binding
                    binding.showAll.setOnClickListener {
                        val bundle = Bundle().apply {
                            putString("param1", this@FilmPreviewAdapter.key)
                        }
                        it.findNavController().navigate(
                            R.id.action_navigation_home_btn_to_fullListFilmsFragment,
                            bundle
                        )
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = ITEM_COUNT + SHOW_ALL_BTN_COUNT

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            ITEM_COUNT -> R.layout.right_arrow
            else -> R.layout.film_preview
        }
    }
}

class MySecondViewHolder(val binding: RightArrowBinding) : RecyclerView.ViewHolder(binding.root)

class MyNewViewHolder(val binding: FilmPreviewBinding) : RecyclerView.ViewHolder(binding.root)