package com.movify.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import java.util.*

@Entity(primaryKeys = ["idInfoLista", "idPelicula"])
data class PeliRefLista(
    val idInfoLista: Long,
    val idPelicula: Int,
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val fecha : Date
)