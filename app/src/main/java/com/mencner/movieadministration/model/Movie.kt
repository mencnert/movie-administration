package com.mencner.movieadministration.model

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class Movie(@JsonProperty("id")
            var id: Long,
            @JsonProperty("name")
            var name: String,
            @JsonProperty("year")
            var year: Int,
            @JsonProperty("genre")
            var genre: String,
            @JsonProperty("director")
            var director: String)