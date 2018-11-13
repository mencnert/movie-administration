package com.mencner.movieadministration

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import com.mencner.movieadministration.model.Movie

class MovieDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)


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
    }

    private fun customizeEditText(resourceId : Int, text: String) {
        val editText = findViewById<EditText>(resourceId)
        editText.setText(text)
    }
}
