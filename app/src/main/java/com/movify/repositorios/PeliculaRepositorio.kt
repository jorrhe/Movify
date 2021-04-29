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



}