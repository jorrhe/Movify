package com.movify.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieList(
    @PrimaryKey val movieListId: Long,
    val listName: String
)