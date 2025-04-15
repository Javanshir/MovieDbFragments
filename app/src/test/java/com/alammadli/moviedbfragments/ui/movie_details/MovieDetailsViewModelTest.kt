package com.alammadli.moviedbfragments.ui.movie_details

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alammadli.moviedbfragments.R
import com.alammadli.moviedbfragments.data.movie_details.Genre
import com.alammadli.moviedbfragments.data.movie_details.MovieDetails
import com.alammadli.moviedbfragments.data.movie_details.MovieDetailsRepository
import com.alammadli.moviedbfragments.utils.MyResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Javanshir on 27.08.24.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MovieDetailsViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var movieDetailsRepository: MovieDetailsRepository
    private lateinit var context: Context

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        movieDetailsRepository = mockk()
        viewModel = MovieDetailsViewModel(movieDetailsRepository)
        context = mockk()
    }

    @Test
    fun `setMovieIdAndStartRequest sets loading state, fetches data and adjusts the state (success case)`() = runTest {
        val movieId = 1
        val movieDetails = mockk<MovieDetails>()
        coEvery { movieDetailsRepository.getMovieById(movieId) } returns MyResult.Success(movieDetails)

        viewModel.setMovieIdAndStartRequest(movieId)

        assertEquals(MyResult.Loading, viewModel.result.value)
        advanceUntilIdle()
        coVerify {
            movieDetailsRepository.getMovieById(movieId)
        }
        assertEquals(MyResult.Success(movieDetails), viewModel.result.value)
    }

    @Test
    fun `setMovieIdAndStartRequest sets loading state, fetches data and adjusts the state (error case)`() = runTest {
        val movieId = 1
        val exception = Exception("Something went wrong")
        coEvery { movieDetailsRepository.getMovieById(movieId) } returns MyResult.Error(exception)

        viewModel.setMovieIdAndStartRequest(movieId)

        assertEquals(MyResult.Loading, viewModel.result.value)
        advanceUntilIdle()
        coVerify {
            movieDetailsRepository.getMovieById(movieId)
        }
        assertEquals(MyResult.Error(exception), viewModel.result.value)
    }

    @Test
    fun `setMovieIdAndStartRequest does not fetch data if it is already loaded`() = runTest {
        val movieId = 1
        val movieDetails = mockk<MovieDetails>()
        coEvery { movieDetailsRepository.getMovieById(movieId) } returns MyResult.Success(movieDetails)
        viewModel.setMovieIdAndStartRequest(movieId)
        advanceUntilIdle()
        viewModel.setMovieIdAndStartRequest(movieId) // Second call
        advanceUntilIdle()

        coVerify(exactly = 1) { movieDetailsRepository.getMovieById(movieId) }
    }

    @Test
    fun `getCommaSeparatedGenreNames returns comma separated genres`() {
        val commaSeparatedGenres = "Action, Adventure, Comedy"
        val genres = arrayListOf(Genre(1, "Action"), Genre(2, "Adventure"), Genre(3, "Comedy"))
        every { context.getString(R.string.genres_with_text, commaSeparatedGenres) } returns "Genres: Action, Adventure, Comedy"

        val result = viewModel.getCommaSeparatedGenreNames(context, genres)

        assertEquals("Genres: Action, Adventure, Comedy", result)
    }

    @Test
    fun `getCommaSeparatedGenreNames returns not available when genres are empty`() {
        val notAvailableText = "Not available"
        val genres = arrayListOf<Genre>()
        every { context.getString(R.string.not_available) } returns notAvailableText
        every { context.getString(R.string.genres_with_text, any()) } returns "Genres: $notAvailableText"

        val result = viewModel.getCommaSeparatedGenreNames(context, genres)

        assertEquals("Genres: Not available", result)
    }

    @Test
    fun `getFormattedRuntime returns formatted time`() {
        val runtime = 130
        every { context.getString(R.string.hours_and_minutes, 2, 10) } returns "Runtime: 2h 10m"

        val result = viewModel.getFormattedRuntime(context, runtime)

        assertEquals("Runtime: 2h 10m", result)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}