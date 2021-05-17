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
    val searchViewModel:SearchViewModel by viewModels {SearchViewModelFactory(repositorioApi)}

    lateinit var movieListsViewModel: ListaGuardadaViewModel
    lateinit var infoPeliculaViewModel:InfoPeliculaViewModel
    lateinit var listaGuardadaViewModel: ListaGuardadaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repositorioListas = ListRepository(this.application)

        val movieListsViewModel: ListaGuardadaViewModel by viewModels { ListaGuardadaViewModelFactory(repositorioListas) }
        this.movieListsViewModel = movieListsViewModel

        val listaGuardadaViewModel:ListaGuardadaViewModel by viewModels { ListaGuardadaViewModelFactory(repositorioListas) }
        this.listaGuardadaViewModel = listaGuardadaViewModel

        val infoPeliculaViewModel:InfoPeliculaViewModel by viewModels{ InfoPeliculaViewModelFactory(repositorioListas, repositorioApi) }
        this.infoPeliculaViewModel = infoPeliculaViewModel

        setContent {
            MovifyApp(inicioViewModel,infoPeliculaViewModel, searchViewModel, movieListsViewModel, listaGuardadaViewModel)
        }

    }
}