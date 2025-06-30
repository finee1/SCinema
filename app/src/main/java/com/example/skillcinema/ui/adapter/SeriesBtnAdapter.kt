package com.example.skillcinema.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.databinding.PageNumberBinding

class SeriesBtnAdapter(private val onClick: (Int) -> Unit) : RecyclerView.Adapter<SeriesBtnVH>() {

    private var values: Int = 0
    private var previousElementId = 0
    private var currentElement = 0

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: Int) {
        this.values = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesBtnVH {
        val binding = PageNumberBinding.inflate(LayoutInflater.from(parent.context))
        return SeriesBtnVH(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SeriesBtnVH, position: Int) {
        if (position == currentElement) {
            with(holder.binding.btnFrames) {
                setTextColor(Color.parseColor("#FFFFFFFF"))
                setBackgroundColor(Color.parseColor("#3D3BFF"))
            }
        } else {
            with(holder.binding.btnFrames) {
                setTextColor(Color.parseColor("#FF000000"))
                setBackgroundColor(Color.parseColor("#00000000"))
            }
        }

        holder.binding.btnFrames.text = (position + 1).toString()
        holder.binding.btnFrames.setOnClickListener {
            previousElementId = currentElement
            currentElement = position.toInt()
            notifyItemChanged(previousElementId)
            notifyItemChanged(currentElement)
            onClick(position)

        }
    }

    override fun getItemCount(): Int = values
}

class SeriesBtnVH(val binding: PageNumberBinding) : RecyclerView.ViewHolder(binding.root)