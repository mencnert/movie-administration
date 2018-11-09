package com.mencner.movieadministration

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.TextView
import com.mencner.movieadministration.model.Movie
import com.mencner.movieadministration.service.MovieDbService


class MovieListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        val movieService = MovieDbService()
        val linearLayout: LinearLayout = findViewById(R.id.info)
        val movies: List<Movie> = movieService.getMovies()
        for (movie in movies) {
            val view = TextView(this)
            view.text = "%d: %s".format(movie.id, movie.name)
            linearLayout.addView(view)
        }
    }
}
