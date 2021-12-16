package com.app.codingtest.movieapp.ui.popularmovie.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.app.codingtest.movieapp.R
import com.app.codingtest.movieapp.common.MovieResult
import com.app.codingtest.movieapp.databinding.FragmentPopularMovieDetailsBinding
import com.app.codingtest.movieapp.domain.entity.moviedetails.MovieDetails
import com.app.codingtest.movieapp.utils.launchAndRepeatWithViewLifecycle
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PopularMovieDetailsFragment : Fragment() {
    private val viewModel by viewModels<PopularMovieDetailsViewModel>()
    private val args: PopularMovieDetailsFragmentArgs by navArgs()
    private var _binding: FragmentPopularMovieDetailsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = args.movieId
        viewModel.fetchMovieDetails(movieId)

        launchAndRepeatWithViewLifecycle {
            viewModel.movieDetailsState.collect { movieResult ->
                when (movieResult) {
                    is MovieResult.Error -> {
                        showErrorMessage()
                    }
                    is MovieResult.Loading -> {
                        setProgressVisibility(movieResult.showProgress)
                    }
                    is MovieResult.Success -> {
                        updateUI(movieResult.data)
                    }
                    is MovieResult.Empty -> {
                    }
                }
            }
        }
    }

    private fun updateUI(data: MovieDetails) {
        with(binding) {
            groupView.visibility = View.VISIBLE
            textOverview.text = data.overView
            if (data.rating > 0.0) {
                textRating.text = getString(R.string.rating, data.rating.toString())
            }
            textTitle.text = data.title
            Glide.with(root.context).load(data.imageUrl)
                .centerCrop()
                .into(imageMovie)
        }
    }

    private fun setProgressVisibility(showProgress: Boolean) {
        with(binding) {
            if (showProgress) {
                progressBar.visibility = View.VISIBLE
                groupView.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun showErrorMessage() {
        Snackbar
            .make(
                binding.root,
                R.string.error,
                Snackbar.LENGTH_LONG
            )
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}