package com.example.skillcinema.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentStaffBinding
import com.example.skillcinema.ui.adapter.StaffFilmAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class StaffFragment : Fragment() {

    private var param1: Int? = null

    private var _binding: FragmentStaffBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StaffViewModel by viewModels()
    private val staffAdapter = StaffFilmAdapter(10)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            param1 = it.getInt(FullListFilmsFragment.ARG_PARAM_ONE)
        }
        _binding = FragmentStaffBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewBestFilmsStaff.adapter = staffAdapter
        viewModel.update(param1!!)

        binding.btnBackStaff.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.staff.onEach {staff ->
            with(binding) {
                Glide.with(imageStaffPhoto.context)
                    .load(staff.posterUrl)
                    .into(imageStaffPhoto)
                staffAdapter.setData(staff.films)

                nameStaff.text = staff.nameRu ?: staff.nameEn
                roleStaff.text = staff.profession ?: ""
                textFilmsStaffCount.text = staff.films.size.toString() + " фильмов"

                toAllFilmography.setOnClickListener {
                    val bundle = Bundle().apply {
                        staff.personId?.let { it1 -> putInt("param1", it1) }
                    }
                    findNavController().navigate(R.id.action_staffFragment_to_filmographyFragment, bundle)
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}