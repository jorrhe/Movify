package com.movify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.movify.repositorios.PeliculaRepositorio
import com.movify.viewmodels.InfoPeliculaViewModel
import com.movify.viewmodels.InicioPeliculaViewModel
import com.movify.viewmodels.SearchViewModel

class MainActivity : ComponentActivity() {

    val repositorio = PeliculaRepositorio()

    val inicioViewModel = InicioPeliculaViewModel(repositorio)
    val infoPeliculaViewModel = InfoPeliculaViewModel(repositorio)
    val searchViewModel = SearchViewModel(repositorio)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovifyApp(inicioViewModel,infoPeliculaViewModel, searchViewModel)
        }

    }
}