package com.movify.repositorios

import com.movify.BuildConfig
import info.movito.themoviedbapi.TmdbApi
import info.movito.themoviedbapi.model.MovieDb

class PeliculaRepositorio {

    private val apiKey = BuildConfig.claveTmdb
    private val apiClient:TmdbApi by lazy { TmdbApi(apiKey) }

    fun getUltimasPeliculas(pagina:Int):List<MovieDb>{
        val peliculas = apiClient.movies.getPopularMovies("es-ES",pagina)

        return peliculas.results
    }

    fun getSearch(query: String): List<MovieDb> {
        val foundMovies = apiClient.search.searchMovie(query, 0, "es-ES", true, 0)

        return foundMovies.results
    }
}