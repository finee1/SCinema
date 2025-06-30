package com.example.skillcinema.ui.onBoardingFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentOnBoarding1Binding
import com.example.skillcinema.ui.HomePageActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoarding1Fragment : Fragment() {

    private var _binding: FragmentOnBoarding1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoarding1Binding.inflate(layoutInflater)

        binding.skipText.setOnClickListener{
            val intent = Intent(activity, HomePageActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        binding.onBoarding1Fragment.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_onBoarding2Fragment)
        }
        return binding.root
    }

}