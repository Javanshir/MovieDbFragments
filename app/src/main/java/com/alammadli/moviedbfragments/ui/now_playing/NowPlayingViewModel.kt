package com.alammadli.moviedbfragments.ui.now_playing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alammadli.moviedbfragments.data.now_playing.Movie
import com.alammadli.moviedbfragments.data.now_playing.NowPlayingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Javanshir on 23.08.24.
 */
@HiltViewModel
class NowPlayingViewModel @Inject constructor(nowPlayingRepository: NowPlayingRepository): ViewModel() {

    val items: Flow<PagingData<Movie>> = nowPlayingRepository.getMoviesStream().cachedIn(viewModelScope)

}