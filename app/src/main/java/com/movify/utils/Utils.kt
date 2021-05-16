package com.movify.utils

import androidx.compose.foundation.lazy.LazyListState
import info.movito.themoviedbapi.model.MovieDb
import org.json.JSONObject

fun getUrlCaratula(pelicula:MovieDb,ancho:String = "w300"):String{
    return "https://image.tmdb.org/t/p/$ancho/${pelicula.posterPath}"
}

fun getUrlBackdrop(pelicula:MovieDb,ancho:String = "w780"):String{
    return "https://image.tmdb.org/t/p/$ancho/${pelicula.backdropPath}"
}

fun procesarServicios(respuesta:String):List<String>{
    val objetoRespuesta = JSONObject(respuesta)

    if(objetoRespuesta.has("results")){

        val resultados = objetoRespuesta.getJSONObject("results")

        if(resultados.has("ES")){

            val es = resultados.getJSONObject("ES")

            if(es.has("flatrate")){

                val imagenes:ArrayList<String> = ArrayList()

                val servicios = es.getJSONArray("flatrate")

                for (i in 0 until servicios.length()) {
                    val servicio = servicios.getJSONObject(i)

                    if(servicio.has("logo_path")){
                        imagenes.add("https://image.tmdb.org/t/p/original${servicio.get("logo_path")}")
                    }
                }

                return imagenes

            }

        }

    }

    return emptyList()
}

// Funciones de extension

fun LazyListState.isScrolledToTheEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1