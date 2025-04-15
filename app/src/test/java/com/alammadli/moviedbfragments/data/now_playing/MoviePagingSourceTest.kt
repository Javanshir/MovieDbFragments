package com.alammadli.moviedbfragments.data.now_playing

import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import androidx.paging.PagingState
import com.alammadli.moviedbfragments.data.network.TheMovieDbRetrofitService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Created by Javanshir on 27.08.24.
 */
class MoviePagingSourceTest {

    private val theMovieDbRetrofitService = mockk<TheMovieDbRetrofitService>()
    private val moviePagingSource = MoviePagingSource(theMovieDbRetrofitService)

    @Test
    fun `load returns Page when on successful response`() = runBlocking {
        val movies = listOf(
            Movie(id = 1, title = "Movie 1", overview = "Overview 1", voteCount = 100, voteAverage = 8.0, posterPath = "/path1.jpg"),
            Movie(id = 2, title = "Movie 2", overview = "Overview 2", voteCount = 150, voteAverage = 7.5, posterPath = "/path2.jpg")
        )
        val nowPlaying = NowPlaying(page = 1, results = ArrayList(movies), totalPages = 2)
        coEvery { theMovieDbRetrofitService.getLatestMovies(INITIAL_PAGE_INDEX) } returns nowPlaying

        val loadResult = moviePagingSource.load(LoadParams.Refresh(INITIAL_PAGE_INDEX, 2, false))

        assertTrue(loadResult is LoadResult.Page)
        val page = loadResult as LoadResult.Page
        assertEquals(movies, page.data)
        assertNull(page.prevKey)
        assertEquals(2, page.nextKey) // Next page should be 2 since totalPages is 2
    }

    @Test
    fun `load returns Error when an exception occurs`() = runBlocking {
        val exception = Exception("Network Error")
        coEvery { theMovieDbRetrofitService.getLatestMovies(INITIAL_PAGE_INDEX) } throws exception

        val loadResult = moviePagingSource.load(LoadParams.Refresh(INITIAL_PAGE_INDEX, 2, false))

        assertTrue(loadResult is LoadResult.Error)
        val errorResult = loadResult as LoadResult.Error
        assertEquals(exception, errorResult.throwable)
    }

    @Test
    fun `getRefreshKey returns correct key`() {
        val movies = listOf(
            Movie(id = 1, title = "Movie 1", overview = "Overview 1", voteCount = 100, voteAverage = 8.0, posterPath = "/path1.jpg"),
            Movie(id = 2, title = "Movie 2", overview = "Overview 2", voteCount = 150, voteAverage = 7.5, posterPath = "/path2.jpg")
        )
        val pagingState = PagingState(
            pages = listOf(
                LoadResult.Page(
                    data = movies,
                    prevKey = 1,
                    nextKey = 3
                )
            ),
            anchorPosition = 1,
            config = mockk(relaxed = true),
            leadingPlaceholderCount = 0
        )

        val refreshKey = moviePagingSource.getRefreshKey(pagingState)

        assertEquals(2, refreshKey)
    }

    @Test
    fun `getRefreshKey returns null when anchorPosition is null`() {
        val pagingState = PagingState<Int, Movie>(
            pages = emptyList(),
            anchorPosition = null,
            config = mockk(relaxed = true),
            leadingPlaceholderCount = 0
        )

        val refreshKey = moviePagingSource.getRefreshKey(pagingState)

        assertNull(refreshKey)
    }
}