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
import com.movify.vistas.*
import androidx.navigation.compose.navigate
import com.movify.componentes.MensajeError
import com.movify.database.MovieList
import com.movify.viewmodels.*

@Composable
fun MovifyApp(
    inicioPeliculaViewModel: InicioPeliculaViewModel,
    infoPeliculaViewModel: InfoPeliculaViewModel,
    searchViewModel: SearchViewModel,
    movieListsViewModel: MovieListsViewModel,
    detailedListViewModel: DetailedListViewModel
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
                        cargarPelicula = { pelicula ->
                            infoPeliculaViewModel.pelicula = pelicula
                            navController.navigate(Vista.InfoPelicula.ruta+"/"+pelicula.id)
                        }
                    )

                }

                composable(Vista.Buscar.ruta) {
                    Buscar(
                        foundMovies = searchViewModel.foundMovies,
                        searchMovies = { query ->
                            searchViewModel.getMovies(query)
                        },
                        cleanSearch = { searchViewModel.cleanSearch() },
                        loadMovie = { movie ->
                            infoPeliculaViewModel.pelicula = movie
                            navController.navigate(Vista.InfoPelicula.ruta+"/"+movie.id)
                        }
                    )
                }

                composable(Vista.Perfil.ruta) {
                    Perfil(
                        listas = movieListsViewModel.list?:listOf(),
                        verLista = { listInfo:MovieList ->
                            detailedListViewModel.listInfo = listInfo
                            detailedListViewModel.loadList(listInfo.movieListId.toInt())
                            navController.navigate(Vista.Lista.ruta)
                        }

                    ) { peli ->
                        infoPeliculaViewModel.pelicula = peli
                        navController.navigate(Vista.InfoPelicula.ruta + "/" + peli.id)
                    }
                }

                composable("${Vista.InfoPelicula.ruta}/{idPelicula}") {backStackEntry ->

                    //val idPelicula = backStackEntry.arguments?.getString("idPelicula")
                    infoPeliculaViewModel.pelicula?.let {pelicula->

                        InfoPelicula(pelicula)

                    }?: MensajeError(stringResource(R.string.error_infopelicula))

                }

                composable("${Vista.Lista.ruta}") {
                    listaUsuario(
                        listInfo = detailedListViewModel.listInfo,
                        list = detailedListViewModel.actualList,
                        loadMovie ={movie ->
                             infoPeliculaViewModel.pelicula = movie
                             navController.navigate(Vista.InfoPelicula.ruta+"/"+movie.id)} ,
                    )
                }


            }
        }

    }

}