package com.mencner.movieadministration

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import com.mencner.movieadministration.adapter.MovieAdapter
import com.mencner.movieadministration.model.Movie
import com.mencner.movieadministration.service.MovieDbService


class MovieListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        val movieService = MovieDbService()
        val listView: ListView = findViewById(R.id.movies_list)

        var l = movieService.getMovies(Movie.EVALUATION)

        val movieAdapter = MovieAdapter(this, R.layout.movie_item, l.toList())

        listView.adapter = movieAdapter
    }
}