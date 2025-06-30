package com.example.skillcinema.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentFullListFilmsBinding
import com.example.skillcinema.databinding.FragmentGalleryBinding
import com.example.skillcinema.ui.adapter.GalleryPagingAdapter
import com.example.skillcinema.ui.adapter.GalleryPreviewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private var param1: Int? = null
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GalleryViewModel by viewModels()

    private val galleryAdapter = GalleryPagingAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            param1 = it.getInt(FullListFilmsFragment.ARG_PARAM_ONE)
        }
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerGallery.adapter = galleryAdapter
        with(binding.btnFrames) {
            setBackgroundColor(Color.parseColor("#3D3BFF"))
            setTextColor(Color.parseColor("#FFFFFFFF"))
        }

        binding.galleryBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }


        fun empty() = viewModel.pagedGallery(param1!!, FRAMES_TYPE_IMAGES).onEach {
            galleryAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        fun ss() = viewModel.pagedGallery(param1!!, SHOOTING_TYPE_IMAGES).onEach {
            galleryAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        fun posters() = viewModel.pagedGallery(param1!!, POSTERS_TYPE_IMAGES).onEach {
                galleryAdapter.submitData(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)


        empty().start()

        binding.galleryBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnFrames.setOnClickListener {
            empty().start()
            with(binding.btnFrames) {
                setBackgroundColor(Color.parseColor("#3D3BFF"))
                setTextColor(Color.parseColor("#FFFFFFFF"))
            }
            with(binding.btnSs) {
                setBackgroundColor(Color.parseColor("#00000000"))
                setTextColor(Color.parseColor("#FF000000"))
            }
            with(binding.btnPosters) {
                setBackgroundColor(Color.parseColor("#00000000"))
                setTextColor(Color.parseColor("#FF000000"))
            }
        }
        binding.btnSs.setOnClickListener {
            ss().start()
            with(binding.btnFrames) {
                setBackgroundColor(Color.parseColor("#00000000"))
                setTextColor(Color.parseColor("#FF000000"))
            }
            with(binding.btnSs) {
                setBackgroundColor(Color.parseColor("#3D3BFF"))
                setTextColor(Color.parseColor("#FFFFFFFF"))
            }
            with(binding.btnPosters) {
                setBackgroundColor(Color.parseColor("#00000000"))
                setTextColor(Color.parseColor("#FF000000"))
            }
        }
        binding.btnPosters.setOnClickListener {
            posters().start()
            with(binding.btnFrames) {
                setBackgroundColor(Color.parseColor("#00000000"))
                setTextColor(Color.parseColor("#FF000000"))
            }
            with(binding.btnSs) {
                setBackgroundColor(Color.parseColor("#00000000"))
                setTextColor(Color.parseColor("#FF000000"))
            }
            with(binding.btnPosters) {
                setBackgroundColor(Color.parseColor("#3D3BFF"))
                setTextColor(Color.parseColor("#FFFFFFFF"))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val SHOOTING_TYPE_IMAGES = "SHOOTING"
        private const val FRAMES_TYPE_IMAGES = "STILL"
        private const val POSTERS_TYPE_IMAGES = "POSTER"

    }

}