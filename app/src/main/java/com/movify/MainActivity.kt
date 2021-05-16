package com.movify

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.movify.repositorios.ListRepository
import com.movify.repositorios.PeliculaRepositorio
import com.movify.viewmodels.*

class MainActivity : ComponentActivity() {

    val repositorio = PeliculaRepositorio()

    lateinit var movieListsViewModel: MovieListsViewModel
    lateinit var detailedListViewModel: DetailedListViewModel
    val inicioViewModel = InicioPeliculaViewModel(repositorio)
    val infoPeliculaViewModel = InfoPeliculaViewModel(repositorio)
    val searchViewModel = SearchViewModel(repositorio)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repositorioListas = ListRepository(this.application)
        movieListsViewModel= MovieListsViewModel(repositorioListas)
        detailedListViewModel = DetailedListViewModel(repositorioListas, repositorio)
        setContent {
            MovifyApp(inicioViewModel,infoPeliculaViewModel, searchViewModel, movieListsViewModel, detailedListViewModel)
        }

    }

}