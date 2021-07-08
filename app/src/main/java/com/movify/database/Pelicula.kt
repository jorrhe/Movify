package com.movify.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import info.movito.themoviedbapi.model.MovieDb

@Entity
data class Pelicula(
    @PrimaryKey
    var idPelicula: Int,
    val nombre:String,
    val caratula : String,
    val informacion:String?,
    val fondo:String?
) {

    fun AMovieDb(): MovieDb {
        val movieDb = MovieDb()
        movieDb.id = idPelicula
        movieDb.title = nombre
        movieDb.posterPath = caratula
        movieDb.backdropPath = fondo
        movieDb.overview = informacion
        return movieDb
    }
}

fun movieDbAPelicula(movieDb: MovieDb): Pelicula{
    return Pelicula(movieDb.id,movieDb.title,movieDb.posterPath?:"", movieDb.overview?:"", movieDb.backdropPath?:"")
}