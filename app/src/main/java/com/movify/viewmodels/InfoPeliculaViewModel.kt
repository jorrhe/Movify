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

    fun getPelicula(id:Int){

        viewModelScope.launch(Dispatchers.IO) {

            listasGuardadas.forEach {lista->
                val idLista = lista.idInfoLista
                agregadaALista[idLista] = repositorioListas.estaPeliculaEnLista(idLista,pelicula.id)
            }

            val peliculaDatosNuevos = repositorioPeliculas.getPelicula(id)

            peliculaDatosNuevos?.let {

                pelicula.apply {
                    genres = peliculaDatosNuevos.genres
                    backdropPath = peliculaDatosNuevos.backdropPath
                    title = peliculaDatosNuevos.title
                    overview = peliculaDatosNuevos.overview
                    voteAverage = peliculaDatosNuevos.voteAverage
                    releaseDate = peliculaDatosNuevos.releaseDate
                    isAdult = peliculaDatosNuevos.isAdult
                }

                peliculasRelacionadas = peliculaDatosNuevos.similarMovies

                imagenesServicios = repositorioPeliculas.getServicios(id)


            }

        }
    }

    fun cambioPelicula(peliculaNueva:MovieDb){
        this.pelicula = peliculaNueva
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