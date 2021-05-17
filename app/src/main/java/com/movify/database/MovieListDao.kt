package com.movify.database


import androidx.room.*

@Dao
interface MovieListDao {

    @Query("SELECT * FROM InfoLista")
    suspend fun getAllLists(): List<InfoLista>

    @Transaction
    @Query("SELECT * FROM InfoLista WHERE idInfoLista = :listId")
    suspend fun getListById(listId: Int): InfoListaConPelis
    
    @Delete
    suspend fun removeMovieFromList(peliRefLista: PeliRefLista)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addMovieToList(peliRefLista: PeliRefLista)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg peliculas: Pelicula)

    //NO SE SI TIENEN USO:

    @Query("SELECT * FROM Pelicula")
    fun getAllMovies(): List<Pelicula>

    @Query("SELECT * FROM Pelicula WHERE idPelicula IN (:movieIds)")
    fun loadAllByIds(movieIds: IntArray): List<Pelicula>

    //@Query("SELECT * FROM movie WHERE title LIKE :text")
    //fun findByTitle(text:String): Movie



    @Delete
    fun delete(pelicula: Pelicula)
}