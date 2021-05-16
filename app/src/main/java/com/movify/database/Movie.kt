package com.movify.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Movie(
    @PrimaryKey val movieId: Int,
    val imdbId: Int


)