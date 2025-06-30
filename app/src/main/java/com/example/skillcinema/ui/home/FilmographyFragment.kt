package com.example.skillcinema.ui.home

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentFilmographyBinding
import com.example.skillcinema.databinding.FragmentStaffBinding
import com.example.skillcinema.ui.adapter.StaffFilmAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FilmographyFragment : Fragment() {

    private var param1: Int? = null

    private var _binding: FragmentFilmographyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StaffViewModel by viewModels()
    private val staffAdapter = StaffFilmAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            param1 = it.getInt(FullListFilmsFragment.ARG_PARAM_ONE)
        }
        _binding = FragmentFilmographyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.RVFilmography.adapter = staffAdapter
        viewModel.update(param1!!)
        with(binding.btnActor) {
            setBackgroundColor(Color.parseColor("#3D3BFF"))
            setTextColor(Color.parseColor("#FFFFFFFF"))
        }
        var films = viewModel.staff.value.films
        viewModel.staff.onEach {
            binding.textNameActor.text =
                viewModel.staff.value.nameRu ?: viewModel.staff.value.nameEn
            films = it.films
            staffAdapter.setData(it.films.filter {
                it.professionKey == "ACTOR" && it.description?.contains(
                    "озвучка",
                    ignoreCase = true
                ) != true
            })
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.btnActor.setOnClickListener {
            staffAdapter.setData(films.filter {
                it.professionKey == "ACTOR" && it.description?.contains(
                    "озвучка",
                    ignoreCase = true
                ) != true
            })
            with(binding.btnActor) {
                setBackgroundColor(Color.parseColor("#3D3BFF"))
                setTextColor(Color.parseColor("#FFFFFFFF"))
            }
            with(binding.btnActorDubbing) {
                setBackgroundColor(Color.parseColor("#00000000"))
                setTextColor(Color.parseColor("#FF000000"))
            }
            with(binding.btnPlayingHimself) {
                setBackgroundColor(Color.parseColor("#00000000"))
                setTextColor(Color.parseColor("#FF000000"))
            }
        }
        binding.btnActorDubbing.setOnClickListener {
            staffAdapter.setData(films.filter {
                it.description?.contains(
                    "озвучка",
                    ignoreCase = true
                ) == true
            })
            with(binding.btnActor) {
                setBackgroundColor(Color.parseColor("#00000000"))
                setTextColor(Color.parseColor("#FF000000"))
            }
            with(binding.btnActorDubbing) {
                setBackgroundColor(Color.parseColor("#3D3BFF"))
                setTextColor(Color.parseColor("#FFFFFFFF"))
            }
            with(binding.btnPlayingHimself) {
                setBackgroundColor(Color.parseColor("#00000000"))
                setTextColor(Color.parseColor("#FF000000"))
            }
        }
        binding.btnPlayingHimself.setOnClickListener {
            staffAdapter.setData(films.filter { it.professionKey == "HIMSELF" || it.professionKey == "HERSELF" })
            with(binding.btnActor) {
                setBackgroundColor(Color.parseColor("#00000000"))
                setTextColor(Color.parseColor("#FF000000"))
            }
            with(binding.btnActorDubbing) {
                setBackgroundColor(Color.parseColor("#00000000"))
                setTextColor(Color.parseColor("#FF000000"))
            }
            with(binding.btnPlayingHimself) {
                setBackgroundColor(Color.parseColor("#3D3BFF"))
                setTextColor(Color.parseColor("#FFFFFFFF"))
            }
        }
    }
}