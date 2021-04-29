package com.movify

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.movify.componentes.BarraInferior
import com.movify.ui.theme.MovifyTheme
import com.movify.viewmodels.InfoPeliculaViewModel
import com.movify.viewmodels.InicioPeliculaViewModel
import com.movify.vistas.*
import androidx.navigation.compose.navigate
import com.movify.componentes.MensajeError

@Composable
fun MovifyApp(
    inicioPeliculaViewModel: InicioPeliculaViewModel,
    infoPeliculaViewModel: InfoPeliculaViewModel
){

    val navController = rememberNavController()

    MovifyTheme {

        Scaffold(
            bottomBar = {
                BarraInferior(navController)
            }
        ){innerPadding ->

            NavHost(
                navController,
                startDestination = Vista.Inicio.ruta,
                modifier = Modifier.padding(innerPadding)
            ) {

                composable(Vista.Inicio.ruta) {
                    Inicio(
                        peliculas = inicioPeliculaViewModel.peliculas,
                        cargarSiguientePagina = {
                            inicioPeliculaViewModel.siguientePagina()
                        },
                        cargarPelicula = {pelicula ->
                            infoPeliculaViewModel.pelicula = pelicula
                            navController.navigate(Vista.InfoPelicula.ruta+"/"+pelicula.id)
                        }
                    )
                }

                composable(Vista.Buscar.ruta) {
                    Buscar()
                }

                composable(Vista.Perfil.ruta) {
                    Perfil()
                }

                composable("${Vista.InfoPelicula.ruta}/{idPelicula}") {backStackEntry ->

                    //val idPelicula = backStackEntry.arguments?.getString("idPelicula")
                    infoPeliculaViewModel.pelicula?.let {pelicula->

                        InfoPelicula(pelicula)

                    }?: MensajeError(stringResource(R.string.error_infopelicula))

                }

            }
        }

    }

}