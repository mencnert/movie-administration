package com.mencner.movieadministration.service

import android.os.StrictMode
import com.mencner.movieadministration.model.Movie
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
        val url = BASE_URL + if (name.equals("")) "/movie?name=" else "/movie?name={}"
        val movies = restTemplate.getForObject(url,
                Array<Movie>::class.java,
                name)
        movies.sortedWith(compareBy{it.name})
        return movies
    }
}