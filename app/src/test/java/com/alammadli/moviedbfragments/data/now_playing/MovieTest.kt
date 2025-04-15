package com.alammadli.moviedbfragments.data.now_playing

import junit.framework.TestCase.assertEquals
import org.junit.Test

/**
 * Created by Javanshir on 27.08.24.
 */
class MovieTest {

    @Test
    fun `getImageUrl returns correct URL with the base URL`() {
        val movie = Movie(
            id = 1,
            title = "Test Movie",
            overview = "Test Overview",
            voteCount = 100,
            voteAverage = 8.5,
            posterPath = "/testPoster.jpg"
        )

        val imageUrl = movie.getImageUrl()

        assertEquals("https://image.tmdb.org/t/p/w500/testPoster.jpg", imageUrl)
    }

    @Test
    fun `getImageUrl handles empty posterPath correctly`() {
        val movie = Movie(
            id = 1,
            title = "Test Movie",
            overview = "Test Overview",
            voteCount = 100,
            voteAverage = 8.5,
            posterPath = ""
        )

        val imageUrl = movie.getImageUrl()

        assertEquals("https://image.tmdb.org/t/p/w500", imageUrl)
    }
}