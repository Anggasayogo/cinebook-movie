package com.cinebookmovie.domain.model

data class DetailMovie(
    val id: Int,
    val title: String?,
    val overview: String?,
    val posterUrl: String?,
    val backdropUrl: String?,
    val releaseDate: String?,
    val runtime: Int?,
    val voteAverage: Double?,
    val genres: List<String>,
    val tagline: String?
){
    val poster: String get() = "https://image.tmdb.org/t/p/w500$posterUrl"
    // Helper property untuk gambar Banner / Backdrop horizontal
    val backdrop: String get() = "https://image.tmdb.org/t/p/w780$backdropUrl"
}