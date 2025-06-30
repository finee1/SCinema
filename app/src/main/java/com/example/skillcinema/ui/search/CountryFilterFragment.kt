package com.example.skillcinema.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.databinding.FragmentCountryFilterBinding
import com.example.skillcinema.entity.CountryFilter
import com.example.skillcinema.ui.adapter.CountryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryFilterFragment : Fragment() {

    private var _binding: FragmentCountryFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by activityViewModels()

    private val adapter = CountryAdapter { setCountry(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listCountry = viewModel.countryFilter

        binding.RVCountryFilter.adapter = adapter
        adapter.setData(viewModel.countryFilter, viewModel.countries)

        binding.filterCountryBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.SVCountry.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.SVCountry.clearFocus()
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                adapter.setData(listCountry.filter {
                    it.country?.contains(
                        text!!,
                        ignoreCase = true
                    ) == true
                }, viewModel.countries)
                return true
            }
        })
    }

    fun setCountry(country: CountryFilter) {
        viewModel.countriesValue = country.country ?: "Неизвестная страна"
        viewModel.countries = country.id
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

