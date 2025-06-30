package com.example.skillcinema.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentCountryFilterBinding
import com.example.skillcinema.databinding.FragmentGenreFilterBinding
import com.example.skillcinema.entity.CountryFilter
import com.example.skillcinema.entity.GenreFilter
import com.example.skillcinema.ui.adapter.CountryAdapter
import com.example.skillcinema.ui.adapter.GenreAdapter

class GenreFilterFragment : Fragment() {

    private var _binding: FragmentGenreFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by activityViewModels()

    private val adapter = GenreAdapter{setCountry(it)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGenreFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listCountry = viewModel.genresFilter

        binding.RVGenreFilter.adapter = adapter
        adapter.setData(listCountry, viewModel.genres)

        binding.filterGenreBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.SVGenre.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.SVGenre.clearFocus()
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                adapter.setData(listCountry.filter{ it.genre?.contains(text!!, ignoreCase = true) == true }, viewModel.genres)
                return true
            }
        })
    }

    fun setCountry(genre: GenreFilter){
        viewModel.genreValue = genre.genre ?: "Любой"
        viewModel.genres = genre.id
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}