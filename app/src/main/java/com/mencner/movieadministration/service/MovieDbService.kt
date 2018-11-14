package com.mencner.movieadministration.service

import android.os.StrictMode
import com.mencner.movieadministration.model.Movie
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate

class MovieDbService {
    private val restTemplate = RestTemplate()
    private val BASE_URL: String = "http://192.168.0.16:8080"

    init{
        restTemplate.messageConverters.add(MappingJackson2HttpMessageConverter())
    }

    init {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    fun getMoviesByName(name: String): Array<Movie> {
        println("name: $name")

        val url = BASE_URL + if (name.equals("")) "/movie?name=" else "/movie?name=$name"
        println("url: $url")
        val movies = restTemplate.getForObject(url,
                Array<Movie>::class.java)
        movies.sortBy { it.name }
        return movies
    }

    fun updateMovie(movie: Movie) {
        val entity = HttpEntity<Movie>(movie)
        val id = movie.id
        restTemplate.exchange("$BASE_URL/movie/$id",
                HttpMethod.PUT,
                entity,
                Movie::class.java)
    }

    fun deleteMovie(movie: Movie) {
        deleteMovie(movie.id)
    }

    fun deleteMovie(id: Long) {
        restTemplate.delete("$BASE_URL/movie/$id")
    }
}