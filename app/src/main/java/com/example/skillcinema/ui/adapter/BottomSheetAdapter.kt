package com.example.skillcinema.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.R
import com.example.skillcinema.databinding.CollectionBottomSheetBinding
import com.example.skillcinema.databinding.CountryGenreFilterBinding
import com.example.skillcinema.entity.CountryFilter

class BottomSheetAdapter(
    private val onClick: (String, Boolean) -> Unit
) :
    RecyclerView.Adapter<BottomSheetCollectionVH>() {

    private var values: LinkedHashMap<String, Int> = linkedMapOf()
    private var keys: List<String> = emptyList()
    private var filmInCollections: List<String> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setCollections(collections: List<String>){
        this.filmInCollections = collections
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(
        data: LinkedHashMap<String, Int>,
        keys: List<String>,
//        filmInCollections: List<String>
    ) {
        this.values = data
        this.keys = keys
//        this.filmInCollections = filmInCollections
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetCollectionVH {
        val binding = CollectionBottomSheetBinding.inflate(LayoutInflater.from(parent.context))
        return BottomSheetCollectionVH(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BottomSheetCollectionVH, position: Int) {
        val key = keys[position]
        var value: Int = values[key]!!

        values.keys

        with(holder.binding) {
            nameBottomCollection.text = key
            countCollectionBottomSheet.text = value.toString()
            if (key in filmInCollections) checkBoxCollection.isChecked = true

            checkBoxCollection.setOnCheckedChangeListener { _, isCheked ->
                when (isCheked) {
                    true -> {
                        onClick(key, true)
                        value += 1
                        countCollectionBottomSheet.text = value.toString()

                    }
                    false -> {
                        onClick(key, false)
                        value -= 1
                        countCollectionBottomSheet.text = value.toString()

                    }
                }
            }
        }

    }

    override fun getItemCount(): Int = values.size
}

class BottomSheetCollectionVH(val binding: CollectionBottomSheetBinding) :
    RecyclerView.ViewHolder(binding.root)