package com.movify.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.movify.R
import com.movify.database.Pelicula
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

fun esTemaOscuro(c: Context, temaSistema:Boolean):Boolean{
    val sharedPreferences = c.getSharedPreferences("MOVIFY",MODE_PRIVATE)
    return sharedPreferences.getBoolean("tema_oscuro", temaSistema)
}

fun cambiarTemaOscuro(c: Context,tema:Boolean){
    val sharedPreferences = c.getSharedPreferences("MOVIFY",MODE_PRIVATE)
    with (sharedPreferences.edit()) {
        putBoolean("tema_oscuro", tema)
        apply()
    }
}

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            return true
        }
    }
    return false
}

// Funciones de extension

fun LazyListState.isScrolledToTheEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1