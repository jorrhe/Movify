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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListaGuardadaViewModel(var repositorioListas: ListRepository): ViewModel() {

    var infoLista: InfoLista by mutableStateOf(InfoLista(-1,"", ""))

    var listaPeliculas : List<Pelicula> by mutableStateOf(emptyList())
        private set

    var listasGuardadas: List<InfoLista> by mutableStateOf(listOf())
        private set

    init{
        viewModelScope.launch(Dispatchers.IO) {
            val resultado = repositorioListas.getAlLists()?:listOf()
            listasGuardadas = resultado
        }
    }

    fun loadList(id:Int){
        viewModelScope.launch(Dispatchers.IO){

            val infoListaConPelis = repositorioListas.getListById(id)

            if (infoListaConPelis != null) {
                infoLista = infoListaConPelis.infoLista
                listaPeliculas = infoListaConPelis.listaPeliculas
            }
        }
    }

}

class ListaGuardadaViewModelFactory(var repositorioListas: ListRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListaGuardadaViewModel(repositorioListas) as T
    }
}