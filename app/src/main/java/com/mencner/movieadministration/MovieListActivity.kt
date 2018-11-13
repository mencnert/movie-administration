package com.mencner.movieadministration

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import com.mencner.movieadministration.adapter.MovieAdapter
import com.mencner.movieadministration.model.Movie
import com.mencner.movieadministration.service.MovieDbService


class MovieListActivity : AppCompatActivity() {

    private val ACTIVITY_EDIT_REQUEST_CODE: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        val movieService = MovieDbService()
        val listView: ListView = findViewById(R.id.movies_list)

        var l = movieService.getMoviesByName("")

        listView.setOnItemClickListener {parent, view, position, id ->
            val currentMovie = l[position]
            val intent = Intent(this, MovieDetailActivity::class.java)

            intent.putExtra(Movie.NAME, currentMovie.name)
            intent.putExtra(Movie.YEAR, currentMovie.year)
            intent.putExtra(Movie.DIRECTOR, currentMovie.director)
            intent.putExtra(Movie.GENRE, currentMovie.genre)
            intent.putExtra(Movie.EVALUATION, currentMovie.evaluation)
            startActivityForResult(intent, ACTIVITY_EDIT_REQUEST_CODE)
        }

        val movieAdapter = MovieAdapter(this, R.layout.movie_item, l.toList())

        listView.adapter = movieAdapter
    }
}