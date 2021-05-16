package com.movify.database

import androidx.room.Entity

@Entity(primaryKeys = ["movieListId", "movieId"])
data class ListCrossRef(
    val movieListId: Long,
    val movieId: Long
)