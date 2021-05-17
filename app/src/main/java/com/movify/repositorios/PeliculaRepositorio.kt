package com.movify.repositorios

import com.movify.BuildConfig
import com.movify.utils.procesarServicios
import info.movito.themoviedbapi.TmdbApi
import info.movito.themoviedbapi.TmdbMovies
import info.movito.themoviedbapi.model.MovieDb
import okhttp3.OkHttpClient
import okhttp3.Request

class PeliculaRepositorio {

    private val apiKey = BuildConfig.claveTmdb
    private val apiClient:TmdbApi by lazy { TmdbApi(apiKey) }

    fun getUltimasPeliculas(pagina:Int):List<MovieDb>{
        val peliculas = apiClient.movies.getPopularMovies("es-ES",pagina)

        return peliculas.results
    }

    fun getPelicula(id:Int):MovieDb?{
        val pelicula = apiClient.movies.getMovie(
            id,
            "es-ES",
            TmdbMovies.MovieMethod.recommendations,
            TmdbMovies.MovieMethod.similar,
            TmdbMovies.MovieMethod.now_playing,
            TmdbMovies.MovieMethod.credits,
            TmdbMovies.MovieMethod.upcoming
        )
        return pelicula
    }

    fun getServicios(id:Int) : List<String>{

        val cliente = OkHttpClient()

        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/$id/watch/providers?api_key=$apiKey")
            .build()

        try {
            val respuesta = cliente.newCall(request).execute().use { it.body()?.string() }

            respuesta?.let {
                return procesarServicios(it)
            }

        }catch (e:Exception){
            println(e)
        }

        return emptyList()
    }

    fun getSearch(query: String): List<MovieDb> {
        val foundMovies = apiClient.search.searchMovie(query, 0, "es-ES", true, 0)

        return foundMovies.results
    }

    fun getMovieById(id:Int):MovieDb{
        val foundMovie = apiClient.movies.getMovie(id, "es-ES")
        return foundMovie
    }
}