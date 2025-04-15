package com.alammadli.moviedbfragments.ui.now_playing

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alammadli.moviedbfragments.R

/**
 * Created by Javanshir on 26.08.24.
 */
class MoviesLoadStateAdapter(
    private val context: Context,
    private val retry: () -> Unit
) : LoadStateAdapter<MoviesLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_latest_movie_load_state, parent, false)
        return LoadStateViewHolder(view)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {

        if (loadState is LoadState.Error) {
            holder.errorMessage.text = loadState.error.localizedMessage
        }
        holder.progressBar.isVisible = loadState is LoadState.Loading
        holder.retryButton.isVisible = loadState is LoadState.Error
        holder.errorMessage.isVisible = loadState is LoadState.Error

        holder.retryButton.setOnClickListener {
            retry.invoke()
        }
    }

    inner class LoadStateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var errorMessage: TextView = itemView.findViewById(R.id.tv_error_message)
        var progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)
        var retryButton: Button = itemView.findViewById(R.id.button_retry)
    }

}