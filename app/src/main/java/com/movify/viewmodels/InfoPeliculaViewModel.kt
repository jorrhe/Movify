package com.movify.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.movify.database.InfoLista
import com.movify.repositorios.ListRepository
import com.movify.repositorios.PeliculaRepositorio
import info.movito.themoviedbapi.model.MovieDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InfoPeliculaViewModel(var repositorioListas: ListRepository, var repositorioPeliculas: PeliculaRepositorio) : ViewModel() {

    var pelicula:MovieDb by mutableStateOf(MovieDb())

    var peliculasRelacionadas:List<MovieDb> by mutableStateOf(emptyList())
        private set

    var imagenesServicios:List<String> by mutableStateOf(emptyList())
        private set

    var listasGuardadas: List<InfoLista> by mutableStateOf(listOf())
        private set

    var agregadaALista: HashMap<Long,Boolean> by mutableStateOf(HashMap())
        private set

    init{
        viewModelScope.launch(Dispatchers.IO) {
            val resultado = repositorioListas.getAlLists()?:listOf()
            listasGuardadas = resultado
        }
    }

    fun getPelicula(idPelicula:Int){

        viewModelScope.launch(Dispatchers.IO) {

            listasGuardadas.forEach {lista->
                val idLista = lista.idInfoLista
                agregadaALista[idLista] = repositorioListas.estaPeliculaEnLista(idLista,idPelicula)
            }

            val peliculaDatosNuevos = repositorioPeliculas.getPelicula(idPelicula)

            peliculaDatosNuevos?.let {

                pelicula.genres = peliculaDatosNuevos.genres
                pelicula.backdropPath = peliculaDatosNuevos.backdropPath
                pelicula.title = peliculaDatosNuevos.title
                pelicula.overview = peliculaDatosNuevos.overview
                pelicula.voteAverage = peliculaDatosNuevos.voteAverage

                peliculasRelacionadas = if(peliculaDatosNuevos.similarMovies.isEmpty()){
                    listOf(peliculaDatosNuevos)
                }else{
                    peliculaDatosNuevos.similarMovies
                }

                imagenesServicios = repositorioPeliculas.getServicios(idPelicula)


            }

        }
    }

    fun cambioPelicula(peliculaNueva:MovieDb){
        pelicula = peliculaNueva
        peliculasRelacionadas = emptyList()
        imagenesServicios = emptyList()
    }

    fun accionDeLista(idLista:Long, pelicula: MovieDb) {

        if(agregadaALista[idLista] == true){
            viewModelScope.launch(Dispatchers.IO) {
                repositorioListas.borrarDeLista(idLista, pelicula)
            }
        }else{
            viewModelScope.launch(Dispatchers.IO) {
                repositorioListas.insertarEnLista(idLista, pelicula)
            }
        }

        agregadaALista[idLista] = !(agregadaALista[idLista]?:false)

    }

}

class InfoPeliculaViewModelFactory(var repositorioListas: ListRepository, var repositorioPeliculas: PeliculaRepositorio): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return InfoPeliculaViewModel(repositorioListas,repositorioPeliculas) as T
    }
}