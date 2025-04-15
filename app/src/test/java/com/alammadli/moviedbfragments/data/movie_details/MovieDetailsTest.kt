package com.alammadli.moviedbfragments.data.movie_details

import junit.framework.TestCase.assertEquals
import org.junit.Test

/**
 * Created by Javanshir on 27.08.24.
 */
class MovieDetailsTest {

    @Test
    fun `getImageUrl returns correct URL with base URL`() {
        val movieDetails = MovieDetails(
            id = 1,
            title = "Test Movie",
            overview = "Test Overview",
            backdropPath = "/testBackdrop.jpg",
            runtime = 120,
            genres = arrayListOf()
        )

        val imageUrl = movieDetails.getImageUrl()
        assertEquals("https://image.tmdb.org/t/p/original/testBackdrop.jpg", imageUrl)
    }
}