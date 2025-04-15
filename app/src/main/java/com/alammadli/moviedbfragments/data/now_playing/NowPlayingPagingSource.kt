package com.alammadli.moviedbfragments.data.now_playing

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alammadli.moviedbfragments.data.network.TheMovieDbRetrofitService

/**
 * Created by Javanshir on 24.08.24.
 */
const val INITIAL_PAGE_INDEX = 1

class MoviePagingSource(private val theMovieDbRetrofitService: TheMovieDbRetrofitService) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPageNumber = params.key ?: INITIAL_PAGE_INDEX
            val response = theMovieDbRetrofitService.getLatestMovies(nextPageNumber)

            LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = if (response.totalPages == response.page) null else response.page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}