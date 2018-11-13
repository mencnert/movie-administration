package com.mencner.movieadministration.model

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class Movie(@JsonProperty(ID)
            var id: Long,
            @JsonProperty(NAME)
            var name: String,
            @JsonProperty(YEAR)
            var year: Int,
            @JsonProperty(GENRE)
            var genre: String,
            @JsonProperty(DIRECTOR)
            var director: String,
            @JsonProperty(EVALUATION)
            var evaluation: Float) {

    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val YEAR = "year"
        const val DIRECTOR = "director"
        const val EVALUATION = "evaluation"
        const val GENRE ="genre"
    }
}