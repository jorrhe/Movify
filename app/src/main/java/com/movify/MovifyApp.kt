package com.movify

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.movify.componentes.BarraInferior
import com.movify.database.InfoLista
import com.movify.ui.theme.MovifyTheme
import com.movify.viewmodels.*
import com.movify.vistas.*
import info.movito.themoviedbapi.model.MovieDb

@Composable
fun MovifyApp(
    inicioPeliculaViewModel: InicioPeliculaViewModel,
    infoPeliculaViewModel: InfoPeliculaViewModel,
    searchViewModel: SearchViewModel,
    movieListsViewModel: ListaGuardadaViewModel,
    listaGuardadaViewModel: ListaGuardadaViewModel
){
    val navController = rememberNavController()

    val cargarPelicula:(MovieDb)->Unit = { pelicula->
        infoPeliculaViewModel.cambioPelicula(pelicula)
        navController.navigate(Vista.InfoPelicula.ruta+"/"+pelicula.id)
    }

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
                        cargarPelicula = cargarPelicula
                    )

                }

                composable(Vista.Buscar.ruta) {
                    Buscar(
                        foundMovies = searchViewModel.foundMovies,
                        searchMovies = { query ->
                            searchViewModel.getMovies(query)
                        },
                        cleanSearch = { searchViewModel.cleanSearch() },
                        loadMovie = cargarPelicula
                    )
                }

                composable(Vista.Perfil.ruta) {
                    Perfil(
                        listas = movieListsViewModel.listasGuardadas,
                        verLista = { listInfo: InfoLista ->
                            listaGuardadaViewModel.loadList(listInfo.idInfoLista.toInt())
                            navController.navigate(Vista.ListaGuardada.ruta+"/"+listInfo.idInfoLista.toInt())
                        }
                    )
                }

                composable("${Vista.InfoPelicula.ruta}/{idPelicula}") {backStackEntry ->

                    val idPelicula = backStackEntry.arguments?.getString("idPelicula")

                    idPelicula?.toInt()?.let { infoPeliculaViewModel.getPelicula(it) }

                    InfoPelicula(
                        pelicula = infoPeliculaViewModel.pelicula,

                        listasGuardadas = infoPeliculaViewModel.listasGuardadas,
                        accionBotonLista = { id, pelicula->
                            infoPeliculaViewModel.accionDeLista(id,pelicula);
                        },
                        agregadaALista = infoPeliculaViewModel.agregadaALista,

                        imagenesServicios = infoPeliculaViewModel.imagenesServicios,

                        peliculasRelacionadas = infoPeliculaViewModel.peliculasRelacionadas,
                        cargarPelicula = cargarPelicula
                    )

                }

                composable("${Vista.ListaGuardada.ruta}/{idLista}") {backStackEntry ->

                    ListaGuardada(
                        infoLista = listaGuardadaViewModel.infoLista,
                        listaPeliculas = listaGuardadaViewModel.listaPeliculas,
                        cargarPelicula = cargarPelicula
                    )
                }


            }
        }

    }

}

