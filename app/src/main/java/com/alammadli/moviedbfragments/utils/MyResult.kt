package com.alammadli.moviedbfragments.utils

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class MyResult<out R> {

    data class Success<out T>(val data: T) : MyResult<T>()
    data class Error(val exception: Exception) : MyResult<Nothing>()
    object Loading : MyResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

/**
 * `true` if [MyResult] is of type [Success] & holds non-null [Success.data].
 */
val MyResult<*>.succeeded
    get() = this is MyResult.Success && data != null