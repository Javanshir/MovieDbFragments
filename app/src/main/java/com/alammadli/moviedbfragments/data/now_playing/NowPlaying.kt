package com.alammadli.moviedbfragments.data.now_playing

import com.alammadli.moviedbfragments.BuildConfig
import com.google.gson.annotations.SerializedName

/**
 * Created by Javanshir on 23.08.24.
 */
data class NowPlaying(
    val page: Int,
    val results: ArrayList<Movie>,
    @SerializedName("total_pages") val totalPages: Int
)

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("poster_path") private val posterPath: String,
) {

    fun getImageUrl(): String {
        return "${BuildConfig.THE_MOVIE_DB_IMAGE_BASE_URL}w500$posterPath"
    }
}