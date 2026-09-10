package com.cinebookmovie.data.mapper

import com.cinebookmovie.data.remote.dto.MovieDto
import com.cinebookmovie.domain.model.Movie

// change extention from DTO to Model
fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate.orEmpty(),
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}