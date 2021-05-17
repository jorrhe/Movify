package com.movify.repositorios

import android.app.Application
import com.movify.database.*
import info.movito.themoviedbapi.model.MovieDb

class ListRepository(application: Application) {

    private val movieListDao: MovieListDao? = MovieRoomDatabase.getDatabase(application)?.movieDao()

    suspend fun getAlLists():List<InfoLista>?{
            return movieListDao?.getAllLists()
    }

    suspend fun getListById(id:Int): InfoListaConPelis? {
        return movieListDao?.getListById(id)
    }

    suspend fun estaPeliculaEnLista(idInfoLista : Long, idPelicula:Int): Boolean {
        val resultado = movieListDao?.estaPeliculaEnLista(idInfoLista,idPelicula)
        return resultado?: false
    }

    suspend fun insertarEnLista(id:Long, peliculaApi: MovieDb){
        val pelicula = movieDbAPelicula(peliculaApi)
        movieListDao?.insertAll(pelicula)
        movieListDao?.addMovieToList(PeliRefLista(id, pelicula.idPelicula))
    }

    suspend fun borrarDeLista(id:Long, pelicula: MovieDb){
        movieListDao?.removeMovieFromList(PeliRefLista(id, pelicula.id))
    }

}