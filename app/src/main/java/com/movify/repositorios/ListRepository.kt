package com.movify.repositorios

import android.app.Application
import com.movify.database.*

class ListRepository(application: Application) {

    private val movieListDao: MovieListDao? = MovieRoomDatabase.getDatabase(application)?.movieDao()

    suspend fun getAlLists():List<InfoLista>?{
            return movieListDao?.getAllLists()
    }

    suspend fun getListById(id:Int): InfoListaConPelis? {
        return movieListDao?.getListById(id)
    }

    suspend fun InsertarEnLista(id:Int, pelicula: Pelicula){
        movieListDao?.insertAll(pelicula)
        movieListDao?.addMovieToList(PeliRefLista(id.toLong(), pelicula.idPelicula.toLong()))
    }

    suspend fun BorrarDeLista(id:Int, pelicula: Pelicula){
        movieListDao?.removeMovieFromList(PeliRefLista(id.toLong(), pelicula.idPelicula.toLong()))
    }

}