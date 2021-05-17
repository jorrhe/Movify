package com.movify.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.movify.database.InfoLista
import com.movify.database.Pelicula
import com.movify.repositorios.ListRepository
import com.movify.repositorios.PeliculaRepositorio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailedListViewModel(var repositorioListas: ListRepository, var repositorioPeliculas: PeliculaRepositorio): ViewModel() {
    var infoLista: InfoLista by mutableStateOf(InfoLista(-1,"", ""))
    var listaPeliculas : List<Pelicula> by mutableStateOf(emptyList())
        private set

    fun loadList(id:Int){
        viewModelScope.launch(Dispatchers.IO){

            val infoListaConPelis = repositorioListas.getListById(id)

            if (infoListaConPelis != null) {
                infoLista = infoListaConPelis.infoLista
                listaPeliculas = infoListaConPelis.listaPeliculas
            }
        }
    }

    fun agregarALista(idLista:Int, pelicula: Pelicula) {
        viewModelScope.launch(Dispatchers.IO) {
            repositorioListas.InsertarEnLista(idLista, pelicula)
        }
    }

    fun eliminarDeLista(idLista:Int, pelicula: Pelicula) {
        viewModelScope.launch(Dispatchers.IO) {
            repositorioListas.BorrarDeLista(idLista, pelicula)
        }
    }

}

class DetailedListViewModelFactory(var repositorioListas: ListRepository, var repositorioPeliculas: PeliculaRepositorio): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailedListViewModel(repositorioListas,repositorioPeliculas) as T
    }
}