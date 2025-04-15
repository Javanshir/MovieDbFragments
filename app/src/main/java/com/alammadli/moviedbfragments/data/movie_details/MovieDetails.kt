package com.alammadli.moviedbfragments.data.movie_details

import com.alammadli.moviedbfragments.BuildConfig
import com.google.gson.annotations.SerializedName

/**
 * Created by Javanshir on 26.08.24.
 */
data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("backdrop_path") private val backdropPath: String,
    val runtime: Int,
    val genres: ArrayList<Genre>,
) {
    fun getImageUrl(): String {
        return "${BuildConfig.THE_MOVIE_DB_IMAGE_BASE_URL}original$backdropPath"
    }
}

data class Genre(val id: Int, val name: String)