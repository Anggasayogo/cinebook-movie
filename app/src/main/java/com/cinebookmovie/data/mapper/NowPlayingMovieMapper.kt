package com.cinebookmovie.data.mapper

import com.cinebookmovie.data.remote.dto.NowPlayingMovieDto
import com.cinebookmovie.domain.model.NowPlayingMovie

fun NowPlayingMovieDto.toMovieDomain(): NowPlayingMovie {
    return NowPlayingMovie(
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