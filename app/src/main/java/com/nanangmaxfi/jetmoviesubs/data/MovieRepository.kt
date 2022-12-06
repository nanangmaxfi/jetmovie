package com.nanangmaxfi.jetmoviesubs.data

import com.nanangmaxfi.jetmoviesubs.model.FakeMovieDataSource
import com.nanangmaxfi.jetmoviesubs.model.FavoriteMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class MovieRepository {
    private val favoriteMovies = mutableListOf<FavoriteMovie>()

    init {
        if (favoriteMovies.isEmpty()){
            FakeMovieDataSource.dummyMovies.forEach{
                favoriteMovies.add(FavoriteMovie(it, false))
            }
        }
    }

    fun getAllMovies(): Flow<List<FavoriteMovie>> {
        return flowOf(favoriteMovies)
    }

    fun getMovieById(movieId: Long): FavoriteMovie {
        return favoriteMovies.first{
            it.movie.id == movieId
        }
    }

    fun updateFavoriteMovie(movieId: Long, isFavorite: Boolean): Flow<Boolean> {
        val index = favoriteMovies.indexOfFirst { it.movie.id == movieId }
        val result = if (index >= 0){
            val favoriteMovie = favoriteMovies[index]
            favoriteMovies[index] = favoriteMovie.copy(movie = favoriteMovie.movie, isFavorite = isFavorite)
            true
        }
        else {
            false
        }
        return flowOf(result)
    }

    fun getAllFavoriteMovies(): Flow<List<FavoriteMovie>> {
        return getAllMovies().map { favoriteMovies ->
            favoriteMovies.filter { movie ->
                movie.isFavorite
            }
        }
    }

    companion object{
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(): MovieRepository =
            instance ?: synchronized(this){
                MovieRepository().apply {
                    instance = this
                }
            }
    }
}