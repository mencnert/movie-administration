package com.mencner.movieadministration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.mencner.movieadministration.adapter.MovieAdapter
import com.mencner.movieadministration.model.Movie
import com.mencner.movieadministration.service.MovieDbService
import org.springframework.web.client.ResourceAccessException


class MovieListActivity : AppCompatActivity() {

    private val ACTIVITY_EDIT_REQUEST_CODE: Int = 2
    private val ACTIVITY_NEW_REQUEST_CODE: Int = 1
    private val SEARCH_TEXT_KEY = "SEARCH"

    private val movieService = MovieDbService()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        try {
            movieService.getMoviesByName("Testing text!@#$%^&*()")
        } catch (ex: ResourceAccessException) {
            Toast.makeText(this, R.string.toast_server, Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val searchText =  data!!.getStringExtra(SEARCH_TEXT_KEY)
        findViewById<EditText>(R.id.edit_search).setText(searchText)
        displayResult(View(this))

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        hideKeyboard()
        outState.putString(SEARCH_TEXT_KEY, findViewById<EditText>(R.id.edit_search).text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val searchedText = savedInstanceState.getString(SEARCH_TEXT_KEY, "")
        findViewById<EditText>(R.id.edit_search).setText(searchedText)
        displayResult(View(this))
    }

    fun displayResult(v: View) {
        hideKeyboard()
        val searchText = findViewById<EditText>(R.id.edit_search).text.toString()
        val listView = findViewById<ListView>(R.id.movies_list)
        var movies: Array<Movie>
        try {
            movies = movieService.getMoviesByName(searchText)
        } catch (ex: ResourceAccessException) {
            Toast.makeText(this, R.string.toast_server, Toast.LENGTH_LONG).show()
            movies = arrayOf<Movie>()
        }
        listView.setOnItemClickListener {_, _, position, _ ->
            val currentMovie = movies[position]
            val intent = Intent(this, MovieDetailActivity::class.java)

            intent.putExtra(Movie.ID, currentMovie.id)
            intent.putExtra(Movie.NAME, currentMovie.name)
            intent.putExtra(Movie.YEAR, currentMovie.year)
            intent.putExtra(Movie.DIRECTOR, currentMovie.director)
            intent.putExtra(Movie.GENRE, currentMovie.genre)
            intent.putExtra(Movie.EVALUATION, currentMovie.evaluation)
            intent.putExtra(SEARCH_TEXT_KEY, searchText)

            startActivityForResult(intent, ACTIVITY_EDIT_REQUEST_CODE)
        }

        val movieAdapter = MovieAdapter(this, R.layout.movie_item, movies.toList())

        listView.adapter = movieAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.add -> newMovie()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun newMovie() {
        val intent = Intent(this, MovieDetailActivity::class.java)
        val searchText = findViewById<EditText>(R.id.edit_search).text.toString()
        intent.putExtra(SEARCH_TEXT_KEY, searchText)

        startActivityForResult(intent, ACTIVITY_NEW_REQUEST_CODE)
    }

    private fun hideKeyboard() {
        findViewById<View>(R.id.view_for_focus).requestFocus()
        try {
            val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (ex: NullPointerException) {
            //
        }
    }
}