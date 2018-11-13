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

    fun getMovies(orderBy: String): Array<Movie> {
        var l = restTemplate.getForObject("$BASE_URL/movies?orderBy={}",
                Array<Movie>::class.java,
                orderBy)
        l.sortedWith(compareBy({it.name}))
        return l
    }
}