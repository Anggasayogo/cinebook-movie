package com.cinebookmovie.data.remote

import com.cinebookmovie.data.remote.dto.MovieDetailResponseDto
import com.cinebookmovie.data.remote.dto.MovieResponseDto
import com.cinebookmovie.data.remote.dto.NowPlayingMovieResponseDto
import com.cinebookmovie.data.remote.dto.TopRatedMovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieResponseDto

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): NowPlayingMovieResponseDto


    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): TopRatedMovieResponseDto

    @GET("movie/{movie_id}")
    suspend fun getDetailMovies(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieDetailResponseDto
}