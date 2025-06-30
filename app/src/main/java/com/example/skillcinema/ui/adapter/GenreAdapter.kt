package com.example.skillcinema.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.R
import com.example.skillcinema.databinding.CountryGenreFilterBinding
import com.example.skillcinema.entity.GenreFilter

class GenreAdapter(private val onClick: (GenreFilter) -> Unit) :
    RecyclerView.Adapter<CountryGenreVH>() {

    private var values: List<GenreFilter> = emptyList()
    private var previousElementId = 0
    private var currentElement = 0

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<GenreFilter>, country: Int?) {
        this.values = data
        currentElement = country ?: 0
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryGenreVH {
        val binding = CountryGenreFilterBinding.inflate(LayoutInflater.from(parent.context))
        return CountryGenreVH(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CountryGenreVH, position: Int) {
        if (position == currentElement) {
            with(holder.binding.countryGenreLayout) {
                setBackgroundResource(R.drawable.shape_search)
            }
        } else {
            with(holder.binding.countryGenreLayout) {
                setBackgroundColor(Color.parseColor("#00000000"))
            }
        }

        holder.binding.textCountryGenre.text = values[position].genre
        holder.binding.countryGenreLayout.setOnClickListener {
            previousElementId = currentElement
            currentElement = position.toInt()
            notifyItemChanged(previousElementId)
            notifyItemChanged(currentElement)
            onClick(values[position])
        }
    }

    override fun getItemCount(): Int = values.size
}