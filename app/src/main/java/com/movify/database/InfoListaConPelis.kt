package com.movify.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class InfoListaConPelis (
    @Embedded val infoLista: InfoLista,
    @Relation(
        parentColumn = "idInfoLista",
        entityColumn = "idPelicula",
        associateBy = Junction(PeliRefLista::class)
    )
    var listaPeliculas: List<Pelicula>

)