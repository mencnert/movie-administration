package com.mencner.movieadministration.service

import android.content.Context
import android.os.StrictMode
import android.widget.Toast
import com.mencner.movieadministration.R
import com.mencner.movieadministration.model.Movie
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestTemplate

class MovieDbService {
    private val restTemplate = RestTemplate()
    private val BASE_URL: String = "http://192.168.0.7:8080"

    init{
        restTemplate.messageConverters.add(MappingJackson2HttpMessageConverter())
    }

    init {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    @Throws(ResourceAccessException::class)
    fun getMoviesByName(name: String): Array<Movie> {
        val url = BASE_URL + if (name.equals("")) "/movie?name=" else "/movie?name=$name"

        val movies = restTemplate.getForObject(url,
                Array<Movie>::class.java)
        movies.sortBy { it.name.toLowerCase() }

        return movies
    }

    @Throws(ResourceAccessException::class)
    fun updateMovie(movie: Movie) {
        val entity = HttpEntity<Movie>(movie)
        val id = movie.id
        restTemplate.exchange("$BASE_URL/movie/$id",
                HttpMethod.PUT,
                entity,
                Movie::class.java)
    }

    @Throws(ResourceAccessException::class)
    fun createNewMovie(movie: Movie) {
        val entity = HttpEntity<Movie>(movie)
        restTemplate.postForObject("$BASE_URL/movie", entity, Movie::class.java)
    }

    @Throws(ResourceAccessException::class)
    fun deleteMovie(id: Long) {
        restTemplate.delete("$BASE_URL/movie/$id")
    }
}