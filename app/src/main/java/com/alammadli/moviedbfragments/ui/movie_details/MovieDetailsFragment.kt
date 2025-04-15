package com.alammadli.moviedbfragments.ui.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alammadli.moviedbfragments.data.movie_details.MovieDetails
import com.alammadli.moviedbfragments.databinding.FragmentMovieDetailsBinding
import com.alammadli.moviedbfragments.ui.base.BaseFragment
import com.alammadli.moviedbfragments.utils.MyResult
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Javanshir on 24.08.24.
 */
@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {

    private val viewModel: MovieDetailsViewModel by viewModels()
    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMovieDetailsBinding {
        return FragmentMovieDetailsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarMovieDetails.setNavigationOnClickListener { findNavController().popBackStack() }

        setMovieIdAndStartRequest()
        viewModel.result.observe(viewLifecycleOwner) { result ->
            handleViewVisibility(result)
            if (result is MyResult.Success) {
                showMovieDetails(result.data)
            }
        }

        binding.buttonRetry.setOnClickListener {
            setMovieIdAndStartRequest()
        }
    }

    private fun setMovieIdAndStartRequest(){
        viewModel.setMovieIdAndStartRequest(args.movieId)
    }

    private fun handleViewVisibility(result: MyResult<MovieDetails>) {
        binding.progressBar.isVisible = result == MyResult.Loading
        binding.viewSomethingIsWrong.isVisible = result is MyResult.Error
        binding.viewMovieDetails.isVisible = result is MyResult.Success
    }

    private fun showMovieDetails(movieDetails: MovieDetails) {
        binding.apply {
            tvTitle.text = movieDetails.title
            tvGenres.text = viewModel.getCommaSeparatedGenreNames(this.root.context, movieDetails.genres)
            tvRuntime.text = viewModel.getFormattedRuntime(this.root.context, movieDetails.runtime)
            tvOverview.text = movieDetails.overview

            Picasso.get().load(movieDetails.getImageUrl()).into(ivMovieBackdrop)
        }
    }

}