package com.example.skillcinema.ui.customViewGroups

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import com.example.skillcinema.R
import com.example.skillcinema.databinding.SelectionFilmsViewGroupBinding
import com.example.skillcinema.ui.adapter.FilmPreviewAdapter

class SelectionFilmsViewGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private val binding: SelectionFilmsViewGroupBinding

    init {
        val inflatedView = inflate(context, R.layout.selection_films_view_group, this)
        binding = SelectionFilmsViewGroupBinding.bind(inflatedView)
        binding.textViewAll.setOnClickListener {
            buttonClicked?.invoke()
        }
    }

    var buttonClicked: (() -> Unit)? = null

    fun setFilmsAdapter(adapter: FilmPreviewAdapter){
        binding.recyclerViewFilmsPrview.adapter = adapter
    }

    fun onClickAllBtn(key: String){
        val bundle = Bundle().apply {
            putString("param1", key)
        }
        findNavController().navigate(R.id.action_navigation_home_btn_to_fullListFilmsFragment, bundle)
    }
    fun setNameSelection(name: String){
        binding.materialTextView.text = name
    }
    fun visibilityAllBtn(size: Int){
        if (size >= 20) binding.textViewAll.visibility = View.VISIBLE
        else binding.textViewAll.visibility = View.INVISIBLE
    }

}