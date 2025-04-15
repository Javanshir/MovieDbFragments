package com.alammadli.moviedbfragments.data.movie_details

import com.alammadli.moviedbfragments.data.network.TheMovieDbRetrofitService
import com.alammadli.moviedbfragments.utils.MyResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

/**
 * Created by Javanshir on 27.08.24.
 */
class MovieDetailsRepositoryTest {

    private lateinit var theMovieDbRetrofitService: TheMovieDbRetrofitService
    private lateinit var movieDetailsRepository: MovieDetailsRepository

    @Before
    fun setUp() {
        theMovieDbRetrofitService = mockk()
        movieDetailsRepository = MovieDetailsRepository(theMovieDbRetrofitService)
    }

    @Test
    fun `getMovieById returns Success when response is successful and body is not null`() = runBlocking {
        val movieId = 1
        val mockMovieDetails = MovieDetails(
            id = movieId,
            title = "Test Movie",
            overview = "Test Overview",
            backdropPath = "/testBackdrop.jpg",
            runtime = 120,
            genres = arrayListOf()
        )
        val mockResponse: Response<MovieDetails> = Response.success(mockMovieDetails)

        coEvery { theMovieDbRetrofitService.getMovieDetails(movieId) } returns mockResponse

        val result = movieDetailsRepository.getMovieById(movieId)

        assertTrue(result is MyResult.Success)
        assertEquals(mockMovieDetails, (result as MyResult.Success).data)
    }

    @Test
    fun `getMovieById returns Error when response is successful but body is null`() = runBlocking {
        val movieId = 1
        val mockResponse: Response<MovieDetails> = Response.success(null)

        coEvery { theMovieDbRetrofitService.getMovieDetails(movieId) } returns mockResponse

        val result = movieDetailsRepository.getMovieById(movieId)

        assertTrue(result is MyResult.Error)
        assertEquals("Response body is null", (result as MyResult.Error).exception.message)
    }

    @Test
    fun `getMovieById returns Error when response body is empty with successful status`() = runBlocking {
        val movieId = 1
        val emptyBody: MovieDetails? = null
        val mockResponse: Response<MovieDetails> = Response.success(emptyBody)

        coEvery { theMovieDbRetrofitService.getMovieDetails(movieId) } returns mockResponse

        val result = movieDetailsRepository.getMovieById(movieId)

        assertTrue(result is MyResult.Error)
        assertEquals("Response body is null", (result as MyResult.Error).exception.message)
    }

    @Test
    fun `getMovieById returns Error when response is not successful`() = runBlocking {
        val movieId = 1
        val mockResponse: Response<MovieDetails> = Response.error(404, mockk(relaxed = true))

        coEvery { theMovieDbRetrofitService.getMovieDetails(movieId) } returns mockResponse

        val result = movieDetailsRepository.getMovieById(movieId)

        assertTrue(result is MyResult.Error)
        assertEquals("Response.error()", (result as MyResult.Error).exception.message)
    }

    @Test
    fun `getMovieById returns Error when an exception is thrown`() = runBlocking {
        val movieId = 1
        val exception = Exception("Network error")

        coEvery { theMovieDbRetrofitService.getMovieDetails(movieId) } throws exception

        val result = movieDetailsRepository.getMovieById(movieId)

        assertTrue(result is MyResult.Error)
        assertEquals("Network error", (result as MyResult.Error).exception.message)
    }
}