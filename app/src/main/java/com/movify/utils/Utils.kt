package com.movify.utils

import androidx.compose.foundation.lazy.LazyListState
import info.movito.themoviedbapi.model.MovieDb

fun getUrlCaratula(pelicula:MovieDb,ancho:String = "w300"):String{
    return "https://image.tmdb.org/t/p/$ancho/${pelicula.posterPath}"
}

fun getUrlBackdrop(pelicula:MovieDb,ancho:String = "w780"):String{
    return "https://image.tmdb.org/t/p/$ancho/${pelicula.backdropPath}"
}

// Funciones de extension

fun LazyListState.isScrolledToTheEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1