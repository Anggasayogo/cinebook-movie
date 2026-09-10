package com.cinebookmovie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cinebookmovie.data.local.dao.MovieDao
import com.cinebookmovie.data.local.entity.FavoriteMovieEntity

@Database(
    entities = [FavoriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}