package com.example.skillcinema.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentFilmBinding
import com.example.skillcinema.databinding.FragmentSeriesBinding
import com.example.skillcinema.databinding.SeriesVhBinding
import com.example.skillcinema.entity.Episode
import com.example.skillcinema.entity.Season
import com.example.skillcinema.ui.adapter.SeriesAdapter
import com.example.skillcinema.ui.adapter.SeriesBtnAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SeriesFragment : Fragment() {

    private var param1: Int? = null
    private var param2: String? = null
    private var _binding: FragmentSeriesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SeriesViewModel by viewModels()
    private val btnAdapter = SeriesBtnAdapter{ setSeries(it) }
    private val seriesAdapter = SeriesAdapter()
    private lateinit var episodes: List<Season>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            param1 = it.getInt(FullListFilmsFragment.ARG_PARAM_ONE)
            param2 = it.getString("param2")
        }
        _binding = FragmentSeriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.seriesBtnRV.adapter = btnAdapter
        binding.seriesRV.adapter = seriesAdapter
        viewModel.updateSerial(param1!!)
        binding.textNameSeries.text = param2

        binding.seriesBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.serial.onEach {
            var episodesCount = 0
            it.items.forEach { episodesCount += it.episodes.size }
            binding.textSeriesAndSeasons.text = "${it.total} сезонов, $episodesCount серий"
            btnAdapter.setData(it.total)
            if (it.items.isNotEmpty()) {
                episodes = it.items
                seriesAdapter.setData(it.items)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    fun setSeries(index: Int){
        seriesAdapter.setEpisodeData(episodes[index].episodes)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}