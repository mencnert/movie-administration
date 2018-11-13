package com.mencner.movieadministration.adapter

import android.content.Context
import android.content.Intent
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.mencner.movieadministration.MovieDetailActivity
import com.mencner.movieadministration.R
import com.mencner.movieadministration.model.Movie

class MovieAdapter(val mContext: Context,
                   @LayoutRes private val resource: Int,
                   private val movies: List<Movie>) :
        ArrayAdapter<Movie>(mContext, resource, movies) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItem = convertView
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(
                    R.layout.movie_item,
                    parent,
                    false
            )
        }

        val currentMovie = movies[position]

        // Display
        // name
        listItem!!.findViewById<TextView>(R.id.name).text = currentMovie.name
        // id
        listItem.findViewById<TextView>(R.id.evaluation).text = currentMovie.evaluation.toString()

        // Onclick
        listItem.setOnClickListener {
            val intent = Intent(mContext, MovieDetailActivity::class.java)

            intent.putExtra(Movie.NAME, currentMovie.name)
            intent.putExtra(Movie.YEAR, currentMovie.year)
            intent.putExtra(Movie.DIRECTOR, currentMovie.director)
            intent.putExtra(Movie.GENRE, currentMovie.genre)
            intent.putExtra(Movie.EVALUATION, currentMovie.evaluation)

            mContext.startActivity(intent)
        }

        return listItem
    }
}