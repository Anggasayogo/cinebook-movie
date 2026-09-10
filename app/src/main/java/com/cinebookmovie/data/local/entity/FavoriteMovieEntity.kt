package com.cinebookmovie.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String,
    val voteAverage: Double
)