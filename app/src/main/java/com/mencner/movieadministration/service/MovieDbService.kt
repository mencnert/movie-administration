package com.mencner.movieadministration.service

import android.os.StrictMode
import com.mencner.movieadministration.model.Movie
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate

class MovieDbService {
    val restTemplate = RestTemplate()
    val BASE_URL: String = "http://192.168.0.16:8080"

    init{
        restTemplate.messageConverters.add(MappingJackson2HttpMessageConverter())
    }

    init {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    fun getMovies(): List<Movie> {
        return restTemplate.getForObject(BASE_URL + "/movies", Array<Movie>::class.java).toList()
    }
}