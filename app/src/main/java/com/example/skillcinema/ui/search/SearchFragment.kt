package com.example.skillcinema.ui.search

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentSearchBinding
import com.example.skillcinema.ui.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val adapter = SearchAdapter{checkWatched(it)}

    private val viewModel: SearchViewModel by activityViewModels()

    private val waiting: Long = 500
    private var cntr: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.RVSearch.adapter = adapter

        binding.btnMenu.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_dashboard_btn_to_filterFragment)
        }

        viewModel.pagedKinopoisk().onEach {
            adapter.submitData(it)
            adapter.addLoadStateListener { loadState ->
                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                    binding.RVSearch.isVisible = false
                    binding.errorSearch.isVisible = true
                } else {
                    binding.RVSearch.isVisible = true
                    binding.errorSearch.isVisible = false
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                if (cntr != null) {
                    cntr!!.cancel();
                }
                cntr = object : CountDownTimer(waiting, 500) {
                    override fun onTick(p0: Long) = Unit

                    override fun onFinish() {
                        viewModel.keyword = text ?: ""
                        viewModel.pagedKinopoisk().onEach {
                            Log.d("SearchFragment", text.toString())
                            adapter.submitData(it)
                            adapter.addLoadStateListener { loadState ->
                                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                                    binding.RVSearch.isVisible = false
                                    binding.errorSearch.isVisible = true
                                } else {
                                    binding.RVSearch.isVisible = true
                                    binding.errorSearch.isVisible = false
                                }
                            }
                        }.launchIn(viewLifecycleOwner.lifecycleScope)
                    }
                }
                cntr!!.start()
                return true
            }
        })
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

}
