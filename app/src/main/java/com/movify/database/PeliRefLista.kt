package com.movify.database

import androidx.room.Entity

@Entity(primaryKeys = ["idInfoLista", "idPelicula"])
data class PeliRefLista(
    val idInfoLista: Long,
    val idPelicula: Int
)