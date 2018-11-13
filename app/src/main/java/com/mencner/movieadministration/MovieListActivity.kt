package com.mencner.movieadministration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ListView
import com.mencner.movieadministration.adapter.MovieAdapter
import com.mencner.movieadministration.model.Movie
import com.mencner.movieadministration.service.MovieDbService


class MovieListActivity : AppCompatActivity() {

    private val ACTIVITY_EDIT_REQUEST_CODE: Int = 0
    private val ACTIVITY_NEW_REQUEST_CODE: Int = 1

    val movieService = MovieDbService()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
    }

    fun displayResult(v: View) {
        val searchText = findViewById<EditText>(R.id.edit_search).text.toString()
        val listView: ListView = findViewById(R.id.movies_list)
        val movies = movieService.getMoviesByName(searchText)

        listView.setOnItemClickListener {parent, view, position, id ->
            val currentMovie = movies[position]
            val intent = Intent(this, MovieDetailActivity::class.java)

            intent.putExtra(Movie.ID, currentMovie.id)
            intent.putExtra(Movie.NAME, currentMovie.name)
            intent.putExtra(Movie.YEAR, currentMovie.year)
            intent.putExtra(Movie.DIRECTOR, currentMovie.director)
            intent.putExtra(Movie.GENRE, currentMovie.genre)
            intent.putExtra(Movie.EVALUATION, currentMovie.evaluation)

            startActivityForResult(intent, ACTIVITY_EDIT_REQUEST_CODE)
        }

        val movieAdapter = MovieAdapter(this, R.layout.movie_item, movies.toList())

        listView.adapter = movieAdapter

        // Hide keyboard
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS)
    }

    fun newMovie(v: View) {
        val intent = Intent(this, MovieDetailActivity::class.java)

        startActivityForResult(intent, ACTIVITY_NEW_REQUEST_CODE)
    }
}