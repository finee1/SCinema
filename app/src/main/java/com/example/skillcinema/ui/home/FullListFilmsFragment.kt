package com.example.skillcinema.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentFullListFilmsBinding
import com.example.skillcinema.ui.adapter.FilmAdapter
import com.example.skillcinema.ui.adapter.PremierAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class FullListFilmsFragment : Fragment() {

    private var param1: String? = null
    private var _binding: FragmentFullListFilmsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FullListFilmsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            param1 = it.getString(ARG_PARAM_ONE)
        }
        _binding = FragmentFullListFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filmAdapter = if (param1 == PREMIERES_KEY) PremierAdapter{checkWatched(it)} else FilmAdapter{checkWatched(it)}

        binding.recyclerViewFullListFilms.adapter = filmAdapter

        binding.nameSelection.text = when(param1){
            PREMIERES_KEY -> HomeFragment.PREMIERS_NAME
            TOP250_KEY -> HomeFragment.TOP250_NAME
            SERIES_KEY -> HomeFragment.SERIES_NAME
            TOP_POPULAR_KEY -> HomeFragment.POPULAR_NAME
            RANDOM_SELECTION_ONE_KEY -> HomeFragment.firstSelectionValue.name
            RANDOM_SELECTION_TWO_KEY -> HomeFragment.secondSelectionValue.name
            else -> UNKNOWN_NAME
        }
        binding.imageButton.setOnClickListener {
            findNavController().popBackStack()
        }

        when (filmAdapter) {
            is FilmAdapter -> viewModel.pagedKinopoisk(param1!!).onEach {
                filmAdapter.submitData(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
            is PremierAdapter -> {
                viewModel.updatePremiers()
                viewModel.premiers.onEach {
                    filmAdapter.setData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }
    }

    fun checkWatched(id: Int): Boolean {
        var ans = true
        runBlocking {
            ans = viewModel.checkFilmInWatched(id)
        }
        return ans
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val UNKNOWN_NAME = "Подборка"
        const val ARG_PARAM_ONE = "param1"
        const val PREMIERES_KEY = "premiers"
        const val TOP250_KEY = "top250"
        const val SERIES_KEY = "series"
        const val TOP_POPULAR_KEY = "popular"
        const val RANDOM_SELECTION_ONE_KEY = "one"
        const val RANDOM_SELECTION_TWO_KEY = "two"
    }

}