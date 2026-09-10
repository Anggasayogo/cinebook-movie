package com.cinebookmovie.data.mapper

import com.cinebookmovie.data.remote.dto.MovieDetailResponseDto
import com.cinebookmovie.domain.model.DetailMovie

fun MovieDetailResponseDto.toDetailMovDomain(): DetailMovie {
    return DetailMovie(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterPath,
        backdropUrl = backdropPath,
        releaseDate = releaseDate,
        runtime = runtime,
        voteAverage = voteAverage,
        genres = genres?.map { it.name } ?: emptyList(),
        tagline = tagline
    )
}