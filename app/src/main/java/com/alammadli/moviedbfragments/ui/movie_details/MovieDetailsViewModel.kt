package com.alammadli.moviedbfragments.ui.movie_details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alammadli.moviedbfragments.R
import com.alammadli.moviedbfragments.data.movie_details.Genre
import com.alammadli.moviedbfragments.data.movie_details.MovieDetails
import com.alammadli.moviedbfragments.data.movie_details.MovieDetailsRepository
import com.alammadli.moviedbfragments.utils.MyResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Javanshir on 24.08.24.
 */
@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val movieDetailsRepository: MovieDetailsRepository) : ViewModel() {

    private val _result = MutableLiveData<MyResult<MovieDetails>>()
    val result: LiveData<MyResult<MovieDetails>> get() = _result

    fun setMovieIdAndStartRequest(movieId: Int) {
        if (_result.value == null || _result.value is MyResult.Error) {
            _result.value = MyResult.Loading
            viewModelScope.launch {
                _result.value = movieDetailsRepository.getMovieById(movieId)
            }
        }
    }

    fun getCommaSeparatedGenreNames(context: Context, genres: ArrayList<Genre>): String {
        val commaSeparatedNames = genres.joinToString(", ") { it.name }
        val appendText = commaSeparatedNames.ifEmpty { context.getString(R.string.not_available) }
        return context.getString(R.string.genres_with_text, appendText)
    }

    fun getFormattedRuntime(context: Context, minutes: Int): String {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        return context.getString(R.string.hours_and_minutes, hours, remainingMinutes)
    }

}