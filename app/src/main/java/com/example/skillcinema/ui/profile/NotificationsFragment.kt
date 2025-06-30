package com.example.skillcinema.ui.profile

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentNotificationsBinding
import com.example.skillcinema.ui.adapter.CollectionsDBAdapter
import com.example.skillcinema.ui.adapter.ProfileAdapter
import com.shawnlin.numberpicker.NumberPicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotificationsViewModel by viewModels()

    private val watchedAdapter = ProfileAdapter({ clearWatchedDB() }, { checkWatched(it) })
    private val interestedAdapter = ProfileAdapter({ clearInterestedDB() }, { checkWatched(it) })
    private val collectionsAdapter = CollectionsDBAdapter { deleteCollections(it) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.watchedRV.adapter = watchedAdapter
        binding.interestedRV.adapter = interestedAdapter
        binding.collectionsRV.adapter = collectionsAdapter

        binding.collectionsRV.layoutManager = object : GridLayoutManager(context, 2) {
            override fun canScrollVertically(): Boolean = false
        }

        binding.showAllWatched.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(FullListBDFragment.TYPE_PARAM, FullListBDFragment.TYPE_WATCHED_ENTITY)
                putString(FullListBDFragment.NAME_SELECTION_PARAM, "Просмотрено")
            }
            findNavController().navigate(
                R.id.action_navigation_notifications_btn_to_fullListBDFragment,
                bundle
            )
        }

        binding.showAllInterested.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(FullListBDFragment.TYPE_PARAM, FullListBDFragment.TYPE_INTERESTED_ENTITY)
                putString(FullListBDFragment.NAME_SELECTION_PARAM, "Вам было интересно")
            }
            findNavController().navigate(
                R.id.action_navigation_notifications_btn_to_fullListBDFragment,
                bundle
            )
        }

        binding.cardLike.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(FullListBDFragment.TYPE_PARAM, FullListBDFragment.TYPE_LIKE_ENTITY)
                putString(FullListBDFragment.NAME_SELECTION_PARAM, "Любимое")
            }
            findNavController().navigate(
                R.id.action_navigation_notifications_btn_to_fullListBDFragment,
                bundle
            )
        }

        binding.cardWantSee.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(FullListBDFragment.TYPE_PARAM, FullListBDFragment.TYPE_WANT_SEE_ENTITY)
                putString(FullListBDFragment.NAME_SELECTION_PARAM, "Хочу посмотреть")
            }
            findNavController().navigate(
                R.id.action_navigation_notifications_btn_to_fullListBDFragment,
                bundle
            )
        }

        binding.watchedCaret.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(FullListBDFragment.TYPE_PARAM, FullListBDFragment.TYPE_WATCHED_ENTITY)
                putString(FullListBDFragment.NAME_SELECTION_PARAM, "Просмотрено")
            }
            findNavController().navigate(
                R.id.action_navigation_notifications_btn_to_fullListBDFragment,
                bundle
            )
        }

        binding.showAllWatched.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(FullListBDFragment.TYPE_PARAM, FullListBDFragment.TYPE_WATCHED_ENTITY)
                putString(FullListBDFragment.NAME_SELECTION_PARAM, "Просмотрено")
            }
            findNavController().navigate(
                R.id.action_navigation_notifications_btn_to_fullListBDFragment,
                bundle
            )
        }

        binding.interestedCaret.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(FullListBDFragment.TYPE_PARAM, FullListBDFragment.TYPE_INTERESTED_ENTITY)
                putString(FullListBDFragment.NAME_SELECTION_PARAM, "Вам было интересно")
            }
            findNavController().navigate(
                R.id.action_navigation_notifications_btn_to_fullListBDFragment,
                bundle
            )
        }

        binding.showAllInterested.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(FullListBDFragment.TYPE_PARAM, FullListBDFragment.TYPE_INTERESTED_ENTITY)
                putString(FullListBDFragment.NAME_SELECTION_PARAM, "Вам было интересно")
            }
            findNavController().navigate(
                R.id.action_navigation_notifications_btn_to_fullListBDFragment,
                bundle
            )
        }

        binding.createCollection.setOnClickListener {
            val dialogBinding = layoutInflater.inflate(R.layout.edit_text_dialog, null)
            val collectionDialog = Dialog(this@NotificationsFragment.requireContext())
            collectionDialog.setContentView(dialogBinding)
            collectionDialog.setCancelable(true)
            collectionDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            collectionDialog.show()
            val editTextDialog = dialogBinding.findViewById<EditText>(R.id.editTextDialog)
            val closeBtn = dialogBinding.findViewById<ImageButton>(R.id.btnCloseDialog)
            val doneBtn = dialogBinding.findViewById<Button>(R.id.btnDoneDialog)

            closeBtn.setOnClickListener { collectionDialog.cancel() }
            doneBtn.setOnClickListener {
                viewModel.addInTypesCollection(editTextDialog.text.toString())
                collectionDialog.cancel()
            }
        }
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allLike.collect {
                    binding.countCardLike.text = it.size.toString()
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allWantSee.collect {
                    binding.countCardWantSee.text = it.size.toString()
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allWatched.collect {
                    watchedAdapter.setData(it)
                    if (it.size <= 20) {
                        binding.showAllWatched.visibility = View.INVISIBLE
                        binding.watchedCaret.visibility = View.INVISIBLE
                    }else {
                        binding.showAllWatched.visibility = View.VISIBLE
                        binding.watchedCaret.visibility = View.VISIBLE
                    }
                    binding.showAllWatched.text = it.size.toString()

                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.allCollections.collect {
                    val list = linkedMapOf<String, Int>()
                    it.forEach {
                        list[it] = viewModel.filmsCount(it)
                    }
                    collectionsAdapter.setData(list, it)
                }
            }
        }


        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allInterested.collect {
                    interestedAdapter.setData(it)
                    if (it.size <= 20) {
                        binding.showAllInterested.visibility = View.INVISIBLE
                        binding.interestedCaret.visibility = View.INVISIBLE
                    } else {
                        binding.showAllInterested.visibility = View.VISIBLE
                        binding.interestedCaret.visibility = View.VISIBLE
                    }
                    binding.showAllInterested.text = it.size.toString()
                }
            }
        }
    }

    fun clearWatchedDB() {
        viewModel.clearWatchedDB()
    }

    fun clearInterestedDB() {
        viewModel.clearInterestedDB()
    }

    fun deleteCollections(collection: String){
        viewModel.deleteCollection(collection)
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