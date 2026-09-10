package com.cinebookmovie.data.mapper

import com.cinebookmovie.data.remote.dto.TopRatedMovieDto
import com.cinebookmovie.domain.model.TopRatedMovie

fun TopRatedMovieDto.toTopRatedMovDomain(): TopRatedMovie {
    return TopRatedMovie(
        adult = adult ?: false,
        backdropPath = backdropPath,
        genreIds = genreIds.orEmpty(),
        id = id,
        title = title.orEmpty(),
        originalLanguage = originalLanguage.orEmpty(),
        originalTitle = originalTitle.orEmpty(),
        overview = overview.orEmpty(),
        popularity = popularity ?: 0.0,
        posterPath = posterPath,
        releaseDate = releaseDate.orEmpty(),
        video = video ?: false,
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0
    )
}