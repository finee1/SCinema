package com.example.skillcinema.ui.search

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentFilterBinding
import com.shawnlin.numberpicker.NumberPicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : Fragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by activityViewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Установка значений из вьюмодели
        with(binding) {
            when (viewModel.type) {
                TYPE_FILM -> btnFilmsFilter.isChecked = true
                TYPE_SERIES -> btnSeriesFilter.isChecked = true
                TYPE_ALL -> btnAllFilter.isChecked = true
            }
            valueCountryFilter.text = viewModel.countriesValue
            valueGenreFilter.text = viewModel.genreValue
            valueYearFilter.text = "от ${viewModel.yearFrom} до ${viewModel.yearTo}"
            valueRatingFilter.text =
                if (viewModel.ratingFrom == 0 && viewModel.ratingTo == 10) "Любой"
                else "от ${viewModel.ratingFrom} до ${viewModel.ratingTo}"
            ratingSlider.setValues(viewModel.ratingFrom.toFloat(), viewModel.ratingTo.toFloat())
            when (viewModel.order) {
                ORDER_YEAR -> btnDateFilter.isChecked = true
                ORDER_POPULAR -> btnPopularFilter.isChecked = true
                ORDER_RATING -> btnRatingFilter.isChecked = true
            }
        }

        //Изменение значений во вьюмодели
        with(binding) {
            btnAllFilter.setOnClickListener { viewModel.type = TYPE_ALL }
            btnFilmsFilter.setOnClickListener { viewModel.type = TYPE_FILM }
            btnSeriesFilter.setOnClickListener { viewModel.type = TYPE_SERIES }
            ratingSlider.addOnChangeListener { slider, _, _ ->
                viewModel.ratingFrom = slider.values[0].toInt()
                viewModel.ratingTo = slider.values[1].toInt()
                valueRatingFilter.text = "от ${viewModel.ratingFrom} до ${viewModel.ratingTo}"
            }
            btnDateFilter.setOnClickListener { viewModel.order = ORDER_YEAR }
            btnPopularFilter.setOnClickListener { viewModel.order = ORDER_POPULAR }
            btnRatingFilter.setOnClickListener { viewModel.order = ORDER_RATING }

            valueCountryFilter.setOnClickListener {
                findNavController().navigate(R.id.action_filterFragment_to_countryFilterFragment)
            }
            valueGenreFilter.setOnClickListener {
                findNavController().navigate(R.id.action_filterFragment_to_genreFilterFragment)
            }

            valueYearFilter.setOnClickListener {
                val dialogBinding = layoutInflater.inflate(R.layout.year_dialog, null)
                val yearDialog = Dialog(this@FilterFragment.requireContext())
                yearDialog.setContentView(dialogBinding)
                yearDialog.setCancelable(true)
                yearDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                yearDialog.show()
                val numPicker1 = dialogBinding.findViewById<NumberPicker>(R.id.numberPicker)
                val numPicker2 = dialogBinding.findViewById<NumberPicker>(R.id.numberPicker2)

                numPicker1.value = viewModel.yearFrom
                numPicker2.value = viewModel.yearTo

                numPicker1.setOnValueChangedListener { _, _, newVal ->
                    viewModel.yearFrom = newVal
                    valueYearFilter.text = "от ${viewModel.yearFrom} до ${viewModel.yearTo}"
                }
                numPicker2.setOnValueChangedListener { _, _, newVal ->
                    viewModel.yearTo = newVal
                    valueYearFilter.text = "от ${viewModel.yearFrom} до ${viewModel.yearTo}"
                }
            }
            btnEnterSettings.setOnClickListener { findNavController().popBackStack() }
            filterBackBtn.setOnClickListener { findNavController().popBackStack() }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TYPE_ALL = "ALL"
        const val TYPE_FILM = "FILM"
        const val TYPE_SERIES = "TV_SERIES"

        const val ORDER_RATING = "RATING"
        const val ORDER_YEAR = "YEAR"
        const val ORDER_POPULAR = "NUM_VOTE"
    }


}