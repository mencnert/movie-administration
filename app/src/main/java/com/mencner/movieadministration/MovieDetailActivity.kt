package com.mencner.movieadministration

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.mencner.movieadministration.model.Movie
import com.mencner.movieadministration.service.MovieDbService

class MovieDetailActivity : AppCompatActivity() {
    val movieService = MovieDbService()

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // if id == 0 -> empty activity for new movie
        if (intent.getLongExtra(Movie.ID, 0) != 0L) {
            val mMovie = Movie( intent.getLongExtra(Movie.ID, 0),
                    intent.getStringExtra(Movie.NAME),
                    intent.getIntExtra(Movie.YEAR, 0),
                    intent.getStringExtra(Movie.GENRE),
                    intent.getStringExtra(Movie.DIRECTOR),
                    intent.getFloatExtra(Movie.EVALUATION, 0f)
            )

            customizeEditText(R.id.name, mMovie.name)
            customizeEditText(R.id.year, mMovie.year.toString())
            customizeEditText(R.id.genre,  mMovie.genre)
            customizeEditText(R.id.director, mMovie.director)
            customizeEditText(R.id.evaluation, mMovie.evaluation.toString())
        } else {
            val deleteButton = findViewById<Button>(R.id.button_delete)
            deleteButton.isEnabled = false
            deleteButton.alpha = .3f
        }
    }

    private fun customizeEditText(resourceId : Int, text: String) {
        val editText = findViewById<EditText>(resourceId)
        editText.setText(text)
    }

    fun saveChanges(view: View) {
        val id = intent.getLongExtra(Movie.ID, 0)
        val name = findViewById<EditText>(R.id.name).text.toString()
        val year = findViewById<EditText>(R.id.year).text.toString().toInt()
        val genre = findViewById<EditText>(R.id.genre).text.toString()
        val director = findViewById<EditText>(R.id.director).text.toString()
        val evaluation = findViewById<EditText>(R.id.evaluation).text.toString().toFloat()

        val movie = Movie(id, name, year, genre, director, evaluation)
        movieService.updateMovie(movie)

        Toast.makeText(this, R.string.toast_saved, Toast.LENGTH_SHORT).show()
        setResult(Activity.RESULT_OK, intent)
        finish()

    }

    fun delete(view: View) {
        movieService.deleteMovie(intent.getLongExtra(Movie.ID, 0))
        Toast.makeText(this, R.string.toast_deleted, Toast.LENGTH_LONG).show()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
