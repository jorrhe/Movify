package com.movify.database


import androidx.compose.runtime.MutableState
import androidx.room.*

@Dao
interface MovieListDao {

    @Query("SELECT * FROM MovieList")
    suspend fun getAllLists(): List<MovieList>

    @Transaction
    @Query("SELECT * FROM MovieList WHERE movieListId = :listId")
    suspend fun getListById(listId: Int): ListWithMovies
    
    @Delete
    suspend fun removeMovieFromList(listCrossRef: ListCrossRef)

    @Insert
    suspend fun addMovieToList(listCrossRef: ListCrossRef)

    @Insert
    suspend fun insertAll(vararg movies: Movie)


    //NO SE SI TIENEN USO:

    @Query("SELECT * FROM movie")
    fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM movie WHERE movieId IN (:movieIds)")
    fun loadAllByIds(movieIds: IntArray): List<Movie>

    //@Query("SELECT * FROM movie WHERE title LIKE :text")
    //fun findByTitle(text:String): Movie



    @Delete
    fun delete(movie: Movie)
}