package com.movify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.movify.repositorios.ListRepository
import com.movify.repositorios.PeliculaRepositorio
import com.movify.viewmodels.*

class MainActivity : ComponentActivity() {

    val repositorioApi = PeliculaRepositorio()

    val inicioViewModel:InicioPeliculaViewModel by viewModels{InicioPeliculaViewModelFactory(repositorioApi)}
    val infoPeliculaViewModel:InfoPeliculaViewModel by viewModels{ InfoPeliculaViewModelFactory(repositorioApi) }
    val searchViewModel:SearchViewModel by viewModels {SearchViewModelFactory(repositorioApi)}

    lateinit var movieListsViewModel:MovieListsViewModel
    lateinit var detailedListViewModel:DetailedListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repositorioListas = ListRepository(this.application)

        val movieListsViewModel:MovieListsViewModel by viewModels { MovieListsViewModelFactory(repositorioListas) }
        this.movieListsViewModel = movieListsViewModel

        val detailedListViewModel:DetailedListViewModel by viewModels { DetailedListViewModelFactory(repositorioListas, repositorioApi) }
        this.detailedListViewModel = detailedListViewModel

        setContent {
            MovifyApp(inicioViewModel,infoPeliculaViewModel, searchViewModel, movieListsViewModel, detailedListViewModel)
        }

    }
}