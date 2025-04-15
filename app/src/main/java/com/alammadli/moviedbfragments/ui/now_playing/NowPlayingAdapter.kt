package com.alammadli.moviedbfragments.ui.now_playing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alammadli.moviedbfragments.R
import com.alammadli.moviedbfragments.data.now_playing.Movie
import com.alammadli.moviedbfragments.databinding.ItemLatestMovieBinding
import com.squareup.picasso.Picasso

/**
 * Created by Javanshir on 26.08.24.
 */
class NowPlayingAdapter(private val onItemClickListener: (movieId: Int) -> Unit) :
    PagingDataAdapter<Movie, NowPlayingAdapter.LatestMovieViewHolder>(MOVIE_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestMovieViewHolder =
        LatestMovieViewHolder(ItemLatestMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: LatestMovieViewHolder, position: Int) {
        val tile = getItem(position)
        if (tile != null) {
            holder.bind(tile)
        }
    }

    inner class LatestMovieViewHolder(private val binding: ItemLatestMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.tvLatestMovieTitle.text = movie.title
            binding.tvMovieOverview.text = movie.overview

            binding.tvMovieVoteAverage.text = binding.root.context.getString(R.string.rating_with_text, movie.voteAverage, movie.voteCount)
            Picasso.get().load(movie.getImageUrl()).into(binding.ivMoviePoster)

            binding.cardView.setOnClickListener {
                onItemClickListener(movie.id)
            }
        }
    }

    companion object {
        private val MOVIE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

}