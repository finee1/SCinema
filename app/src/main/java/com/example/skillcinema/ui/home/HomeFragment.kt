package com.example.skillcinema.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentHomeBinding
import com.example.skillcinema.entity.RandomSelection
import com.example.skillcinema.ui.adapter.FilmPreviewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private val premiersAdapter = FilmPreviewAdapter(FullListFilmsFragment.PREMIERES_KEY){checkWatched(it)}
    private val top250Adapter = FilmPreviewAdapter(FullListFilmsFragment.TOP250_KEY){checkWatched(it)}
    private val topPopularAdapter = FilmPreviewAdapter(FullListFilmsFragment.TOP_POPULAR_KEY){checkWatched(it)}
    private val seriesAdapter = FilmPreviewAdapter(FullListFilmsFragment.SERIES_KEY){checkWatched(it)}
    private val filmAdapterOne = FilmPreviewAdapter(FullListFilmsFragment.RANDOM_SELECTION_ONE_KEY){checkWatched(it)}
    private val filmAdapterTwo = FilmPreviewAdapter(FullListFilmsFragment.RANDOM_SELECTION_TWO_KEY){checkWatched(it)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.premiersSection.setFilmsAdapter(premiersAdapter)
        binding.top250Selection.setFilmsAdapter(top250Adapter)
        binding.topPopularSelection.setFilmsAdapter(topPopularAdapter)
        binding.seriesSelection.setFilmsAdapter(seriesAdapter)
        binding.filmsSelectionOne.setFilmsAdapter(filmAdapterOne)
        binding.filmsSelectionTwo.setFilmsAdapter(filmAdapterTwo)

        viewModel.isLoading.onEach {
            when (it) {
                true -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                false -> binding.progressBar.visibility = View.GONE
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.premiers.onEach {
            binding.premiersSection.buttonClicked = {
                val bundle = Bundle().apply {
                    putString(FullListFilmsFragment.ARG_PARAM_ONE, FullListFilmsFragment.PREMIERES_KEY)
                }
                findNavController().navigate(R.id.action_navigation_home_btn_to_fullListFilmsFragment, bundle)
            }
            binding.premiersSection.setNameSelection(PREMIERS_NAME)
            binding.premiersSection.visibilityAllBtn(it.size)
            premiersAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.top250.onEach {
            binding.top250Selection.buttonClicked = {
                val bundle = Bundle().apply {
                    putString(FullListFilmsFragment.ARG_PARAM_ONE, FullListFilmsFragment.TOP250_KEY)
                }
                findNavController().navigate(R.id.action_navigation_home_btn_to_fullListFilmsFragment, bundle)
            }
            binding.top250Selection.setNameSelection(TOP250_NAME)
            binding.top250Selection.visibilityAllBtn(it.size)
            top250Adapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.topPopular.onEach {
            binding.topPopularSelection.buttonClicked = {
                val bundle = Bundle().apply {
                    putString(FullListFilmsFragment.ARG_PARAM_ONE, FullListFilmsFragment.TOP_POPULAR_KEY)
                }
                findNavController().navigate(R.id.action_navigation_home_btn_to_fullListFilmsFragment, bundle)
            }
            binding.topPopularSelection.setNameSelection(POPULAR_NAME)
            binding.topPopularSelection.visibilityAllBtn(it.size)
            topPopularAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.series.onEach {
            binding.seriesSelection.buttonClicked = {
                val bundle = Bundle().apply {
                    putString(FullListFilmsFragment.ARG_PARAM_ONE, FullListFilmsFragment.SERIES_KEY)
                }
                findNavController().navigate(R.id.action_navigation_home_btn_to_fullListFilmsFragment, bundle)
            }
            binding.seriesSelection.setNameSelection(SERIES_NAME)
            binding.seriesSelection.visibilityAllBtn(it.size)
            seriesAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.filmsOne.onEach {
            viewModel.updateRandomSelection1(firstSelectionValue.country, firstSelectionValue.genre)
            binding.filmsSelectionOne.buttonClicked = {
                val bundle = Bundle().apply {
                    putString(FullListFilmsFragment.ARG_PARAM_ONE, FullListFilmsFragment.RANDOM_SELECTION_ONE_KEY)
                }
                findNavController().navigate(R.id.action_navigation_home_btn_to_fullListFilmsFragment, bundle)
            }
            binding.filmsSelectionOne.setNameSelection(firstSelectionValue.name)
            binding.filmsSelectionOne.visibilityAllBtn(it.size)
            filmAdapterOne.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.filmsTwo.onEach {
            viewModel.updateRandomSelection2(secondSelectionValue.country, secondSelectionValue.genre)
            binding.filmsSelectionTwo.buttonClicked = {
                val bundle = Bundle().apply {
                    putString(FullListFilmsFragment.ARG_PARAM_ONE, FullListFilmsFragment.RANDOM_SELECTION_TWO_KEY)

                }
                findNavController().navigate(R.id.action_navigation_home_btn_to_fullListFilmsFragment, bundle)
            }
            binding.filmsSelectionTwo.setNameSelection(secondSelectionValue.name)
            binding.filmsSelectionTwo.visibilityAllBtn(it.size)
            filmAdapterTwo.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun checkWatched(id: Int): Boolean {
        var ans = true
        runBlocking {
                ans = viewModel.checkFilmInWatched(id)
        }
        return ans
    }

    companion object{
        const val PREMIERS_NAME = "Премьеры"
        const val TOP250_NAME = "Топ-250"
        const val POPULAR_NAME = "Популярное"
            const val SERIES_NAME = "Сериалы"
        private val listSelections = listOf(
            //Россия
            RandomSelection("Российские триллеры", 34, 1),
            RandomSelection("Российские детективы", 34, 5),
            RandomSelection("Российские боевики", 34, 11),
            RandomSelection("Российские драмы", 34, 2),
            //США
            RandomSelection("Комедии США", 1, 13),
            RandomSelection("Боевики США", 1, 11),
            RandomSelection("Фантастика США", 1, 6),
            RandomSelection("Вестерны США", 1, 10),
            //Япония
            RandomSelection("Японское аниме", 16, 24),
            //Франция
            RandomSelection("Французские драмы", 1, 2),
            RandomSelection("Французские мультфильмы", 1, 18)
        )
        val firstSelectionValue = listSelections.random()
        val secondSelectionValue = listSelections.random()
    }
}