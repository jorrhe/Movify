package com.movify.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ListWithMovies (
    @Embedded val movieList: MovieList,
    @Relation(
        parentColumn = "movieListId",
        entityColumn = "movieId",
        associateBy = Junction(ListCrossRef::class)
    )
    var movies: List<Movie>

)