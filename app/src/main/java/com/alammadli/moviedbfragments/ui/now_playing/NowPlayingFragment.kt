package com.alammadli.moviedbfragments.ui.now_playing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.alammadli.moviedbfragments.databinding.FragmentNowPlayingBinding
import com.alammadli.moviedbfragments.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Javanshir on 23.08.24.
 */
@AndroidEntryPoint
class NowPlayingFragment : BaseFragment<FragmentNowPlayingBinding>() {

    private val viewModel: NowPlayingViewModel by viewModels()

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentNowPlayingBinding {
        return FragmentNowPlayingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nowPlayingAdapter = NowPlayingAdapter { movieId ->
            findNavController().navigate(NowPlayingFragmentDirections.actionNowPlayingFragmentToMovieDetailsFragment(movieId))
        }

        nowPlayingAdapter.addLoadStateListener { loadState ->
            binding.rvNowPlaying.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.noResultsView.isVisible = loadState.source.refresh is LoadState.Error
            binding.buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
        }

        binding.buttonRetry.setOnClickListener {
            nowPlayingAdapter.retry()
        }

        binding.rvNowPlaying.adapter = nowPlayingAdapter
        binding.rvNowPlaying.adapter = nowPlayingAdapter.withLoadStateFooter(
            footer = MoviesLoadStateAdapter(view.context) { nowPlayingAdapter.retry() }
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.items.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED).collect {
                nowPlayingAdapter.submitData(it)
            }
        }
    }
}