package com.cinebookmovie.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int
){
    // Helper property untuk mendapatkan Full URL Gambar Poster
    val posterUrl: String get() = "https://image.tmdb.org/t/p/w500$posterPath"
    // Helper property untuk gambar Banner / Backdrop horizontal
    val backdropUrl: String get() = "https://image.tmdb.org/t/p/w780$backdropPath"
}