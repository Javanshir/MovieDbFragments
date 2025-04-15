package com.alammadli.moviedbfragments.data.now_playing

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alammadli.moviedbfragments.data.network.TheMovieDbRetrofitService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Javanshir on 24.08.24.
 */
class NowPlayingRepository @Inject constructor(private val theMovieDbRetrofitService: TheMovieDbRetrofitService) {

    fun getMoviesStream(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(theMovieDbRetrofitService) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

}