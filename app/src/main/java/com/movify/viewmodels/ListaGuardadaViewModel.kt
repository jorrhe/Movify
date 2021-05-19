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

            val infoListaLeida = repositorioListas.getListById(id)
            val peliculas = repositorioListas.getPeliculasByListId(id)

            if (infoListaLeida != null && peliculas!=null) {
                infoLista = infoListaLeida
                listaPeliculas = peliculas
            }
            println("LEIDOOO")
            println(infoLista)
            println(listaPeliculas)
        }
    }

    fun quitarPeliculaDeLista(idLista:Long,idPelicula:Int){

        val listaMutada = listaPeliculas.toMutableList()
        listaMutada.removeIf { it.idPelicula==idPelicula }

        listaPeliculas = listaMutada.toList()
    }

}

class ListaGuardadaViewModelFactory(var repositorioListas: ListRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListaGuardadaViewModel(repositorioListas) as T
    }
}