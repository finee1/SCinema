package com.example.skillcinema.ui.onBoardingFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skillcinema.databinding.FragmentOnBoarding3Binding
import com.example.skillcinema.ui.HomePageActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoarding3Fragment : Fragment() {

    private var _binding: FragmentOnBoarding3Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoarding3Binding.inflate(layoutInflater)

        binding.skipText.setOnClickListener{
            val intent = Intent(activity, HomePageActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        binding.onBoarding3Fragment.setOnClickListener {
            val intent = Intent(activity, HomePageActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        return binding.root
    }

}