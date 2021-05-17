package com.movify.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InfoLista(
    @PrimaryKey val idInfoLista: Long,
    val nombre: String,
    val icono: String
)