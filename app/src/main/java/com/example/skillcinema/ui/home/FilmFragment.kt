package com.example.skillcinema.ui.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.WindowCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.data.db.*
import com.example.skillcinema.databinding.FragmentFilmBinding
import com.example.skillcinema.entity.SimilarFilmPreview
import com.example.skillcinema.entity.StaffListItem
import com.example.skillcinema.ui.adapter.BottomSheetAdapter
import com.example.skillcinema.ui.adapter.GalleryPreviewAdapter
import com.example.skillcinema.ui.adapter.SimilarFilmPreviewAdapter
import com.example.skillcinema.ui.adapter.StaffAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilmFragment : Fragment() {

    private var param1: Int? = null
    private var param2: Int? = null
    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FilmViewModel by viewModels()
    private var activityColor: Int? = null

    private val actorsAdapter = StaffAdapter { staffId ->
        onStaffItemClick(staffId)
    }
    private val staffAdapter = StaffAdapter { staffId ->
        onStaffItemClick(staffId)
    }
    private val galleryAdapter = GalleryPreviewAdapter()
    private val similarFilmsAdapter =
        SimilarFilmPreviewAdapter { similarFilm -> onFilmItemClick(similarFilm) }


    override fun onStart() {
        super.onStart()
        activity?.let { WindowCompat.setDecorFitsSystemWindows(it.window, false) }
        activityColor = activity!!.window.statusBarColor
        activity!!.window.statusBarColor = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let {
            param1 = it.getInt(FullListFilmsFragment.ARG_PARAM_ONE)
        }
        _binding = FragmentFilmBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewActors.adapter = actorsAdapter
        binding.recyclerViewStaff.adapter = staffAdapter
        binding.recyclerViewGallery.adapter = galleryAdapter
        binding.recyclerViewSimilarFilms.adapter = similarFilmsAdapter
        if(param2 == null){
            viewModel.update(param1!!)
        } else viewModel.update(param2!!)

        viewModel.similarFilms.onEach {
            similarFilmsAdapter.setData(it)
            if (it.isNotEmpty()){
                binding.textSimilarFilms.visibility = View.VISIBLE
                binding.recyclerViewSimilarFilms.visibility = View.VISIBLE
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.staffList.onEach {
            val actors = it.filter { it.professionKey == "ACTOR" }
            val staff = it.filter { it.professionKey != "ACTOR" }
            actorsAdapter.setData(actors)
            staffAdapter.setData(staff)
            if (actors.isNotEmpty()){
                binding.textView5.visibility = View.VISIBLE
                binding.recyclerViewActors.visibility = View.VISIBLE
            }
            if (staff.isNotEmpty()){
                binding.textView7.visibility = View.VISIBLE
                binding.recyclerViewStaff.visibility = View.VISIBLE
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.gallery.onEach {
            galleryAdapter.setData(it)
            if (it.isNotEmpty()){
                binding.textView9.visibility = View.VISIBLE
                binding.textView10.visibility = View.VISIBLE
                binding.imageView5.visibility = View.VISIBLE
                binding.recyclerViewGallery.visibility = View.VISIBLE
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.filmInfo.onEach {
            val film = viewModel.filmInfo.value
            val idFilm = it.kinopoiskId
            if (film.serial == true) {
                viewModel.updateSerial(param1!!)
                viewModel.serial.onEach {
                    binding.seriesLayoutFF.visibility = View.VISIBLE
                    val seasons = it.total
                    var series = 0
                    it.items.forEach {
                        series += it.episodes.size
                    }
                    binding.btnSeasonsSeriesAllFF.setOnClickListener {
                        val bundle = Bundle().apply {
                            putString("param2", film.nameRu ?: film.nameOriginal)
                            idFilm?.let { it1 -> putInt("param1", it1) }
                        }
                        findNavController().navigate(
                            R.id.action_filmFragment_to_seriesFragment,
                            bundle
                        )
                    }
                    binding.textSeriesCountFF.text = "$seasons сезонов, $series серий"
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }

            param2 = film.kinopoiskId

            binding.textView10.setOnClickListener {
                val bundle = Bundle().apply {
                    putInt(FullListFilmsFragment.ARG_PARAM_ONE, film.kinopoiskId!!)
                }
                findNavController().navigate(R.id.action_filmFragment_to_galleryFragment, bundle)
            }

            fun onCollectionItemClick(collection: String, addOrDelete: Boolean) {
                when (addOrDelete) {
                    true -> {
                        viewModel.addInCollection(
                            CollectionsEntity(
                                id = null,
                                kinopoiskId = film.kinopoiskId ?: 0,
                                genres = film.genres[0].genre ?: "",
                                nameOriginal = film.nameOriginal,
                                nameRu = film.nameRu,
                                posterUrl = film.posterUrl,
                                ratingKinopoisk = film.ratingKinopoisk,
                                collection = collection
                            )
                        )
                    }
                    false -> {
                        viewModel.deleteFromCollection(
                            film.kinopoiskId ?: 0, collection
                        )
                    }
                }
            }

            val bottomSheetAdapter = BottomSheetAdapter { collection, addOrDelete ->
                onCollectionItemClick(
                    collection,
                    addOrDelete
                )
            }

            with(binding) {
                if (film.logoUrl != null) {
                    Glide.with(imageLogo.context)
                        .load(film.logoUrl)
                        .into(imageLogo)
                } else {
                    imageLogo.visibility = View.GONE
                    textLogo.visibility = View.VISIBLE
                    textLogo.text = film.nameRu ?: film.nameOriginal
                }
                if (film.coverUrl != null) {
                    Glide.with(mainFilmPoster.context)
                        .load(film.coverUrl)
                        .centerCrop()
                        .into(mainFilmPoster)
                } else {
                    Glide.with(mainFilmPoster.context)
                        .load(film.posterUrl)
                        .centerCrop()
                        .into(mainFilmPoster)
                }
                val rating =
                    if (film.ratingKinopoisk != null) film.ratingKinopoisk.toString() + ", " else ""

                textCountryLengthAge.text =
                    "${film.countries[0].country}, ${film.filmLength.toString()} мин., ${film.ratingAgeLimits ?: "18+"}"
                textYearGenre.text =
                    "${film.year.toString()}, ${film.genres.joinToString { it.genre.toString() }}"
                textRatingName.text =
                    "$rating ${film.nameRu ?: film.nameOriginal}"
                textShortDescription.text = film.shortDescription ?: film.slogan
                if (textShortDescription.text != "") textShortDescription.visibility = View.VISIBLE

                with(textDescription) {
                    text = film.description
                    setIsExpanded(false)
                    if (text != "") visibility = View.VISIBLE
                }


            }

            viewModel.btnLikeIsChecked.onEach {
                binding.btnLike.isChecked = it
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel.btnWillWatchIsChecked.onEach {
                binding.btnWillWatch.isChecked = it
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel.checkFilmInDBLike(kinopoiskId = film.kinopoiskId!!)
            viewModel.checkFilmInDBWantSee(kinopoiskId = film.kinopoiskId!!)

            binding.btnWillWatch.setOnClickListener {
                when (binding.btnWillWatch.isChecked) {
                    false -> {
                        viewModel.deleteFromDBWantSee(kinopoiskId = film.kinopoiskId!!)
                    }
                    true -> {
                        viewModel.addDBWantSee(
                            WantSeeEntity(
                                kinopoiskId = film.kinopoiskId ?: 0,
                                nameRu = film.nameRu ?: film.nameOriginal,
                                nameOriginal = film.nameOriginal ?: "",
                                posterUrl = film.posterUrl,
                                ratingKinopoisk = film.ratingKinopoisk,
                                genres = film.genres[0].genre
                            )
                        )
                    }
                }
            }

            binding.btnLike.setOnClickListener {
                when (binding.btnLike.isChecked) {
                    false -> {
                        viewModel.deleteFromDBLike(kinopoiskId = film.kinopoiskId!!)
                    }
                    true -> {
                        viewModel.addDBLike(
                            LikeEntity(
                                kinopoiskId = film.kinopoiskId ?: 0,
                                nameRu = film.nameRu ?: film.nameOriginal,
                                nameOriginal = film.nameOriginal ?: "",
                                posterUrl = film.posterUrl,
                                ratingKinopoisk = film.ratingKinopoisk,
                                genres = film.genres[0].genre
                            )
                        )
                    }
                }
            }

            if (film.kinopoiskId != 0) {
                viewModel.addInInterested(
                    InterestedEntity(
                        kinopoiskId = film.kinopoiskId,
                        nameRu = film.nameRu,
                        nameOriginal = film.nameOriginal,
                        posterUrl = film.posterUrl,
                        ratingKinopoisk = film.ratingKinopoisk,
                        genres = film.genres[0].genre
                    )
                )
            }
            viewModel.btnWatched.onEach {
                binding.btnWatched.isChecked = it
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel.checkFilmInWatched(film.kinopoiskId!!)

            binding.btnWatched.setOnClickListener {
                when (binding.btnWatched.isChecked) {
                    true -> viewModel.addInWatched(
                        WatchedEntity(
                            kinopoiskId = film.kinopoiskId,
                            nameRu = film.nameRu,
                            nameOriginal = film.nameOriginal,
                            posterUrl = film.posterUrl,
                            ratingKinopoisk = film.ratingKinopoisk,
                            genres = film.genres[0].genre
                        )
                    )
                    false -> viewModel.deleteFromWatched(film.kinopoiskId!!)
                }
            }


            binding.btnShare.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, film.webUrl)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }

            binding.btnCollections.setOnClickListener {
                lifecycleScope.launch {
                    lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                        viewModel.allCollections.collect {
                            val list = linkedMapOf<String, Int>()
                            it.forEach {
                                list[it] = viewModel.filmsCount(it)
                            }
                            bottomSheetAdapter.setData(list, it)
                        }
                    }
                }
                bottomSheetAdapter.setCollections(viewModel.collectionsWithFilm.value)
                val dialogBinding = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
                val collectionDialog = BottomSheetDialog(this@FilmFragment.requireContext())
                collectionDialog.setContentView(dialogBinding)
                collectionDialog.setCancelable(true)
                collectionDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                collectionDialog.show()

                val poster = dialogBinding.findViewById<ImageView>(R.id.posterBottomSheet)
                val nameBottomSheet = dialogBinding.findViewById<TextView>(R.id.nameBottomSheet)
                val yearGenreBottomSheet =
                    dialogBinding.findViewById<TextView>(R.id.yearGenreBottomSheet)
                val collectionsRV =
                    dialogBinding.findViewById<RecyclerView>(R.id.collectionsRVBottomSheet)
                val addCollection = dialogBinding.findViewById<TextView>(R.id.textAddCollection)
                val ratingBottomSheet = dialogBinding.findViewById<TextView>(R.id.ratingBottomSheet)
                val wantSeeCheckBox =
                    dialogBinding.findViewById<CheckBox>(R.id.checkBoxWSBottomSheet)
                val likeCheckBox =
                    dialogBinding.findViewById<CheckBox>(R.id.checkBoxLikeBottomSheet)
                val wantSeeCount = dialogBinding.findViewById<TextView>(R.id.countWSBottomSheet)
                val likeCount = dialogBinding.findViewById<TextView>(R.id.countLikeBottomSheet)
                val closeBtnBottomSheet: ImageButton = dialogBinding.findViewById(R.id.closeBtnBottomSheet)

                closeBtnBottomSheet.setOnClickListener { collectionDialog.cancel() }

                collectionsRV.adapter = bottomSheetAdapter

                likeCheckBox.isChecked = viewModel.btnLikeIsChecked.value
                wantSeeCheckBox.isChecked = viewModel.btnWillWatchIsChecked.value

                var likeCountValue = viewModel.allLike.value.size
                var wantSeeCountValue = viewModel.allWantSee.value.size
                likeCount.text = likeCountValue.toString()
                wantSeeCount.text = wantSeeCountValue.toString()

                likeCheckBox.setOnCheckedChangeListener { _, isCheked ->
                    when (isCheked) {
                        true -> {
                            viewModel.addDBLike(
                                LikeEntity(
                                    kinopoiskId = film.kinopoiskId ?: 0,
                                    nameRu = film.nameRu ?: film.nameOriginal,
                                    nameOriginal = film.nameOriginal ?: "",
                                    posterUrl = film.posterUrl,
                                    ratingKinopoisk = film.ratingKinopoisk,
                                    genres = film.genres[0].genre,
                                )
                            )
                            likeCountValue += 1
                            likeCount.text = likeCountValue.toString()
                        }
                        false -> {
                            viewModel.deleteFromDBLike(film.kinopoiskId!!)
                            likeCountValue -= 1
                            likeCount.text = likeCountValue.toString()
                        }
                    }
                }

                wantSeeCheckBox.setOnCheckedChangeListener { _, isCheked ->
                    when (isCheked) {
                        true -> {
                            viewModel.addDBWantSee(
                                WantSeeEntity(
                                    kinopoiskId = film.kinopoiskId ?: 0,
                                    nameRu = film.nameRu ?: film.nameOriginal,
                                    nameOriginal = film.nameOriginal ?: "",
                                    posterUrl = film.posterUrl,
                                    ratingKinopoisk = film.ratingKinopoisk,
                                    genres = film.genres[0].genre
                                )
                            )
                            wantSeeCountValue += 1
                            wantSeeCount.text = wantSeeCountValue.toString()
                        }
                        false -> {
                            viewModel.deleteFromDBWantSee(film.kinopoiskId!!)
                            wantSeeCountValue -= 1
                            wantSeeCount.text = wantSeeCountValue.toString()
                        }
                    }
                }

                addCollection.setOnClickListener {
                    val addCollectionDialog =
                        layoutInflater.inflate(R.layout.edit_text_dialog, null)
                    val nameDialog = Dialog(this@FilmFragment.requireContext())
                    nameDialog.setContentView(addCollectionDialog)
                    nameDialog.setCancelable(true)
                    nameDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    nameDialog.show()
                    val editTextDialog =
                        addCollectionDialog.findViewById<EditText>(R.id.editTextDialog)
                    val closeBtn =
                        addCollectionDialog.findViewById<ImageButton>(R.id.btnCloseDialog)
                    val doneBtn = addCollectionDialog.findViewById<Button>(R.id.btnDoneDialog)

                    closeBtn.setOnClickListener { nameDialog.cancel() }
                    doneBtn.setOnClickListener {
                        viewModel.addInTypesCollection(editTextDialog.text.toString())
                        collectionDialog.cancel()
                    }
                }

                Glide.with(poster.context)
                    .load(film.posterUrl)
                    .centerCrop()
                    .into(poster)

                ratingBottomSheet.text = film.ratingKinopoisk.toString()
                if (film.ratingKinopoisk == null) ratingBottomSheet.visibility = View.INVISIBLE
                nameBottomSheet.text = film.nameRu ?: film.nameOriginal
                yearGenreBottomSheet.text = "${film.year}, ${film.genres[0].genre}"

                collectionDialog.setContentView(dialogBinding)
                collectionDialog.setCancelable(true)
                collectionDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                collectionDialog.show()
            }


        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    fun onFilmItemClick(item: SimilarFilmPreview?) {
        item?.filmId?.let { viewModel.update(it) }
    }

    fun onStaffItemClick(item: StaffListItem?) {
        val bundle = Bundle().apply {
            item?.staffId?.let { putInt("param1", it) }
        }
        findNavController().navigate(R.id.action_filmFragment_to_staffFragment, bundle)
    }

    @SuppressLint("ResourceAsColor")
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        activity?.let { WindowCompat.setDecorFitsSystemWindows(it.window, true) }
        activity!!.window.statusBarColor = activityColor!!

    }

}