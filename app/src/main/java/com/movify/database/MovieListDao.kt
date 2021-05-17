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

    @Query("SELECT EXISTS(SELECT * FROM pelireflista WHERE idInfoLista = :idInfoLista AND idPelicula = :idPelicula )")
    fun estaPeliculaEnLista(idInfoLista : Long, idPelicula:Int) : Boolean


    @Delete
    fun delete(pelicula: Pelicula)
}