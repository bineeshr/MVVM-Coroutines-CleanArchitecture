package com.app.codingtest.movieapp.ui.popularmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.codingtest.movieapp.R
import com.app.codingtest.movieapp.common.MovieResult
import com.app.codingtest.movieapp.databinding.FragmentPopularMovieBinding
import com.app.codingtest.movieapp.domain.entity.popularmovie.PopularMovie
import com.app.codingtest.movieapp.utils.PaginationScrollListener
import com.app.codingtest.movieapp.utils.launchAndRepeatWithViewLifecycle
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PopularMovieFragment : Fragment() {

    private val movieViewModel by viewModels<PopularMovieViewModel>()
    lateinit var adapter: MovieAdapter
    private var _binding: FragmentPopularMovieBinding? = null
    private val binding get() = _binding!!
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var currentPage: Int = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MovieAdapter(onMovieClick = { popularMovie ->
            val bundle = bundleOf("movie_id" to popularMovie.id)
            findNavController().navigate(
                R.id.action_navigation_movie_to_navigation_movie_details,
                bundle
            )
        })
        binding.recyclerviewMovie.adapter = adapter
        movieViewModel.fetchPopularMovies(currentPage)
        launchAndRepeatWithViewLifecycle {
            movieViewModel.movieState.collect { movieResult ->
                when (movieResult) {
                    is MovieResult.Success -> {
                        setData(movieResult)
                    }
                    is MovieResult.Error -> {
                        showErrorMessage()
                    }
                    is MovieResult.Loading -> {
                        setProgressVisibility(movieResult.showProgress)
                    }

                    is MovieResult.Empty -> { }
                }

            }
        }
        val layoutManager = binding.recyclerviewMovie.layoutManager as LinearLayoutManager
        binding.recyclerviewMovie.addOnScrollListener(object :
            PaginationScrollListener(layoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1
                movieViewModel.fetchPopularMovies(currentPage)
            }
        })
    }

    private fun setProgressVisibility(showProgress: Boolean) {
        with(binding) {
            if (showProgress) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun setData(movieResult: MovieResult.Success<MutableList<PopularMovie>>) {
        if (currentPage == 1) {
            adapter.updateList(movieResult.data)
            adapter.addLoadingFooter()
            if (currentPage == movieViewModel.movieTotalPage) {
                adapter.removeLoadingFooter()
                isLastPage = true
            }
        } else {
            if (movieResult.data.isNotEmpty()) {
                adapter.removeLoadingFooter()
                isLoading = false
                adapter.updateList(movieResult.data)
                adapter.addLoadingFooter()
            } else {
                adapter.removeLoadingFooter()
                isLoading = false
                isLastPage = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
}