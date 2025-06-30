package com.example.skillcinema.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentFullListBDBinding
import com.example.skillcinema.databinding.FragmentNotificationsBinding
import com.example.skillcinema.ui.adapter.FullListBDAdapter
import com.example.skillcinema.ui.home.FullListFilmsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class FullListBDFragment : Fragment() {

    private var typeList: Int? = null
    private var nameSelection: String? = null
    private var _binding: FragmentFullListBDBinding? = null
    private val binding get() = _binding!!
    private val adapterDB = FullListBDAdapter{checkWatched(it)}

    private val viewModel: FullListBDViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            typeList = it.getInt(TYPE_PARAM)
            nameSelection = it.getString(NAME_SELECTION_PARAM)
        }
        _binding = FragmentFullListBDBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.RVFullListDB.adapter = adapterDB
        binding.nameSelectionFullListDB.text = nameSelection

        binding.backBtnFullListDB.setOnClickListener {
            findNavController().popBackStack()
        }

        when(typeList){
            TYPE_COLLECTION -> {
                viewModel.loadCollection(nameSelection!!)
                viewModel.collection.onEach {
                    adapterDB.setData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
            TYPE_LIKE_ENTITY -> {
                lifecycleScope.launch {
                    lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                        viewModel.allLike.collect {
                            adapterDB.setData(it)
                        }
                    }
                }
            }
            TYPE_WANT_SEE_ENTITY -> {
                lifecycleScope.launch {
                    lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                        viewModel.allWantSee.collect {
                            adapterDB.setData(it)
                        }
                    }
                }
            }
            TYPE_WATCHED_ENTITY -> {
                lifecycleScope.launch {
                    lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                        viewModel.allWatched.collect {
                            adapterDB.setData(it)
                        }
                    }
                }
            }
            TYPE_INTERESTED_ENTITY -> {
                lifecycleScope.launch {
                    lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                        viewModel.allInterested.collect {
                            adapterDB.setData(it)
                        }
                    }
                }
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

    companion object{
        const val TYPE_PARAM = "type param"
        const val NAME_SELECTION_PARAM = "name selection"

        const val TYPE_COLLECTION = 1
        const val TYPE_LIKE_ENTITY = 2
        const val TYPE_WANT_SEE_ENTITY = 3
        const val TYPE_WATCHED_ENTITY = 4
        const val TYPE_INTERESTED_ENTITY = 5
    }

}