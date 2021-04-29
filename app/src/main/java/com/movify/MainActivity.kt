package com.movify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.movify.repositorios.PeliculaRepositorio
import com.movify.viewmodels.InfoPeliculaViewModel
import com.movify.viewmodels.InicioPeliculaViewModel

class MainActivity : ComponentActivity() {

    val repositorio = PeliculaRepositorio()

    val inicioViewModel = InicioPeliculaViewModel(repositorio)
    val infoPeliculaViewModel = InfoPeliculaViewModel(repositorio)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovifyApp(inicioViewModel,infoPeliculaViewModel)
        }

    }
}