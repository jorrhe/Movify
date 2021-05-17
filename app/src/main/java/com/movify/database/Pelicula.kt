package com.movify.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import info.movito.themoviedbapi.model.MovieDb
import java.util.*

@Entity
data class Pelicula(
    @PrimaryKey(autoGenerate = true) val idPelicula: Int,
    val idApi: Int,
    val nombre:String?,
    val caratula : String?
) {
    fun AMovieDb(): MovieDb {
        val movieDb = MovieDb()
        movieDb.id = idApi
        movieDb.title = nombre
        movieDb.posterPath = caratula
        return movieDb
    }
}