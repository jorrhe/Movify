package com.movify.utils

import androidx.compose.foundation.lazy.LazyListState
import com.movify.R
import com.movify.database.Pelicula
import info.movito.themoviedbapi.model.MovieDb
import org.json.JSONObject


fun getUrlCaratula(pelicula:MovieDb,ancho:String = "w300"):String{
    return "https://image.tmdb.org/t/p/$ancho/${pelicula.posterPath}"
}

fun getUrlBackdrop(rutaImagenFondo:String,ancho:String = "w780"):String{
    return "https://image.tmdb.org/t/p/$ancho/$rutaImagenFondo"
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
                        imagenes.add("https://image.tmdb.org/t/p/original${servicio.getString("logo_path")}")
                    }
                }

                return imagenes

            }

        }

    }

    return emptyList()
}

fun convertirListaAMovieDb(listaPeliculas: List<Pelicula>): List<MovieDb> {
    return listaPeliculas.map { it.AMovieDb() }
}

fun getIconoLista(id:Long): Int =
    when(id){
        1L -> R.drawable.ic_favoritos
        2L -> R.drawable.ic_vistas
        else -> R.drawable.ic_ver_mas_tarde
    }

// Funciones de extension

fun LazyListState.isScrolledToTheEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1