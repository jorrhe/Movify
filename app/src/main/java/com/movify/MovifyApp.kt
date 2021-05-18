package com.movify

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.movify.componentes.BarraInferior
import com.movify.componentes.BarraSuperior
import com.movify.database.InfoLista
import com.movify.ui.theme.MovifyTheme
import com.movify.ui.theme.VerdeMovify
import com.movify.utils.cambiarTemaOscuro
import com.movify.utils.esTemaOscuro
import com.movify.viewmodels.InfoPeliculaViewModel
import com.movify.viewmodels.InicioPeliculaViewModel
import com.movify.viewmodels.ListaGuardadaViewModel
import com.movify.viewmodels.SearchViewModel
import com.movify.vistas.*
import info.movito.themoviedbapi.model.MovieDb


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MovifyApp(
    inicioPeliculaViewModel: InicioPeliculaViewModel,
    infoPeliculaViewModel: InfoPeliculaViewModel,
    searchViewModel: SearchViewModel,
    movieListsViewModel: ListaGuardadaViewModel,
    listaGuardadaViewModel: ListaGuardadaViewModel
){
    val navController = rememberNavController()

    val c = LocalContext.current

    val temaOsuroPorDefecto = esTemaOscuro(c,isSystemInDarkTheme())

    var temaOscuro by rememberSaveable { mutableStateOf(temaOsuroPorDefecto) }

    val cambiarTema:()->Unit ={
        temaOscuro = !temaOscuro
        cambiarTemaOscuro(c,temaOscuro)
    }

    MovifyTheme(darkTheme = temaOscuro) {

        var mostrarBarraSuperior by rememberSaveable { mutableStateOf(false) }
        var tituloBarraSuperior by rememberSaveable { mutableStateOf("") }

        val systemUiController = rememberSystemUiController()
        val useDarkIcons = MaterialTheme.colors.isLight

        SideEffect {

            var color = if(!useDarkIcons){
                Color(0xFF272727)
            }else{
                VerdeMovify
            }

            systemUiController.setStatusBarColor(
                color = color,
                darkIcons = useDarkIcons
            )
        }

        Scaffold(
            topBar ={

                AnimatedVisibility(visible = mostrarBarraSuperior) {
                    BarraSuperior(tituloBarraSuperior)
                }

            },
            bottomBar = {
                BarraInferior(navController)
            }
        ){innerPadding ->

            NavHostMovify(
                navController = navController,
                innerPadding = innerPadding,
                inicioPeliculaViewModel = inicioPeliculaViewModel,
                infoPeliculaViewModel = infoPeliculaViewModel,
                searchViewModel = searchViewModel,
                movieListsViewModel = movieListsViewModel,
                listaGuardadaViewModel = listaGuardadaViewModel,

                accionBarraSuperior = {titulo,mostrar->
                    tituloBarraSuperior = titulo
                    mostrarBarraSuperior = mostrar
                },

                temaOscuro = temaOscuro,
                cambiarTema = cambiarTema
            )

        }

    }

}

@Composable
fun NavHostMovify(

    navController: NavHostController,
    innerPadding: PaddingValues,

    inicioPeliculaViewModel: InicioPeliculaViewModel,
    infoPeliculaViewModel: InfoPeliculaViewModel,
    searchViewModel: SearchViewModel,
    movieListsViewModel: ListaGuardadaViewModel,
    listaGuardadaViewModel: ListaGuardadaViewModel,

    accionBarraSuperior:(String,Boolean)->Unit,

    temaOscuro:Boolean,
    cambiarTema:()->Unit
){

    val cargarPelicula:(MovieDb)->Unit = { pelicula->
        infoPeliculaViewModel.cambioPelicula(pelicula)
        navController.navigate(Vista.InfoPelicula.ruta+"/"+pelicula.id)
    }

    NavHost(
        navController,
        startDestination = Vista.Inicio.ruta,
        modifier = Modifier.padding(innerPadding)
    ) {

        composable(Vista.Inicio.ruta) {

            accionBarraSuperior("",false)

            Inicio(
                peliculas = inicioPeliculaViewModel.peliculas,
                cargarSiguientePagina = {
                    inicioPeliculaViewModel.siguientePagina()
                },
                cargarPelicula = cargarPelicula
            )

        }

        composable(Vista.Buscar.ruta) {

            accionBarraSuperior(stringResource(Vista.Buscar.idEtiqueta),true)

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

            accionBarraSuperior(stringResource(Vista.Perfil.idEtiqueta),true)

            Perfil(
                listas = movieListsViewModel.listasGuardadas,
                verLista = { listInfo: InfoLista ->
                    listaGuardadaViewModel.loadList(listInfo.idInfoLista.toInt())
                    navController.navigate(Vista.ListaGuardada.ruta+"/"+listInfo.idInfoLista.toInt())
                },
                cambiarTema = cambiarTema,
                temaOscuro = temaOscuro
            )
        }

        composable("${Vista.InfoPelicula.ruta}/{idPelicula}") {backStackEntry ->

            accionBarraSuperior("",false)

            val idPelicula = backStackEntry.arguments?.getString("idPelicula")

            idPelicula?.toInt()?.let { infoPeliculaViewModel.getPelicula(it) }

            InfoPelicula(
                pelicula = infoPeliculaViewModel.pelicula,

                listasGuardadas = infoPeliculaViewModel.listasGuardadas,
                agregadaALista = infoPeliculaViewModel.agregadaALista,
                accionBotonLista = { id, pelicula->
                    infoPeliculaViewModel.accionDeLista(id,pelicula);
                },
                borrarDeLista = { idLista->
                    idPelicula?.toInt()?.let {
                        listaGuardadaViewModel.quitarPeliculaDeLista(idLista,it)
                    }
                },

                imagenesServicios = infoPeliculaViewModel.imagenesServicios,

                peliculasRelacionadas = infoPeliculaViewModel.peliculasRelacionadas,
                cargarPelicula = cargarPelicula
            )

        }

        composable("${Vista.ListaGuardada.ruta}/{idLista}") {backStackEntry ->

            accionBarraSuperior(listaGuardadaViewModel.infoLista.nombre,true)

            ListaGuardada(
                listaPeliculas = listaGuardadaViewModel.listaPeliculas,
                cargarPelicula = cargarPelicula
            )
        }


    }
}