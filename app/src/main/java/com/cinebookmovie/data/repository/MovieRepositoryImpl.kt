package com.cinebookmovie.data.repository

import com.cinebookmovie.data.mapper.toDomain
import com.cinebookmovie.data.mapper.toMovieDomain
import com.cinebookmovie.data.mapper.toTopRatedMovDomain
import com.cinebookmovie.data.mapper.toDetailMovDomain
import com.cinebookmovie.data.remote.TmdbApiService
import com.cinebookmovie.domain.model.DetailMovie
import com.cinebookmovie.domain.model.Movie
import com.cinebookmovie.domain.model.NowPlayingMovie
import com.cinebookmovie.domain.model.TopRatedMovie
import com.cinebookmovie.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: TmdbApiService
) : MovieRepository {

    override suspend fun getPopularMovies(): List<Movie> {
        val response = apiService.getPopularMovies()
        return response.results.map { it.toDomain() }
    }

    override suspend fun getNowPlayingMovies(): List<NowPlayingMovie> {
        val response = apiService.getNowPlayingMovies()
        return response.results.map { it.toMovieDomain() }
    }

    override suspend fun getTopRatedMovies(): List<TopRatedMovie> {
        val response = apiService.getTopRatedMovies()
        return response.results.map { it.toTopRatedMovDomain() }
    }

    override suspend fun getDetailMovies(movieId: Int): DetailMovie {
        val response = apiService.getDetailMovies(movieId = movieId)
        return response.toDetailMovDomain()
    }
}