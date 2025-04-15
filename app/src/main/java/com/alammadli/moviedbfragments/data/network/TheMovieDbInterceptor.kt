package com.alammadli.moviedbfragments.data.network

import com.alammadli.moviedbfragments.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request

/**
 * Created by Javanshir on 23.08.24.
 */
class TheMovieDbInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

        val request: Request = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer ${BuildConfig.THE_MOVIE_DB_AUTH_TOKEN}")
            .build()

        return chain.proceed(request)
    }
}