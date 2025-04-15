package com.alammadli.moviedbfragments.data.network

import com.alammadli.moviedbfragments.data.movie_details.MovieDetails
import com.alammadli.moviedbfragments.data.now_playing.NowPlaying
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Javanshir on 23.08.24.
 */
interface TheMovieDbRetrofitService {

    @GET("movie/now_playing")
    suspend fun getLatestMovies(@Query("page") page: Int): NowPlaying

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId: Int): Response<MovieDetails>

}