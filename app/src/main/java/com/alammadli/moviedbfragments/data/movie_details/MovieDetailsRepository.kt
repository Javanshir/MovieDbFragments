package com.alammadli.moviedbfragments.data.movie_details

import com.alammadli.moviedbfragments.data.network.TheMovieDbRetrofitService
import com.alammadli.moviedbfragments.utils.MyResult
import javax.inject.Inject

/**
 * Created by Javanshir on 26.08.24.
 */
class MovieDetailsRepository @Inject constructor(private val theMovieDbRetrofitService: TheMovieDbRetrofitService) {

    suspend fun getMovieById(movieId: Int): MyResult<MovieDetails> {
        return try {
            val response = theMovieDbRetrofitService.getMovieDetails(movieId)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    MyResult.Success(responseBody)
                } else {
                    MyResult.Error(Exception("Response body is null"))
                }
            } else {
                MyResult.Error(Exception(response.message()))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }

}