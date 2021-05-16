package com.movify.vistas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.movify.database.MovieList
import com.movify.ui.theme.MovifyTheme
import info.movito.themoviedbapi.model.MovieDb
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
//TODO: Crear vista de una lista de pelis

@Composable
fun ListaGuardada(listInfo:MovieList, list: List<MovieDb>, loadMovie:(MovieDb)->Unit){
    Column{
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(listInfo.listName, fontSize = 30.sp, modifier = Modifier.padding(12.dp))
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                modifier=Modifier.size(30.dp)
            )
        }

        ListaPeliculas(peliculas = list, cargarPelicula = loadMovie)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewListaGuardada(){
    MovifyTheme() {
       // listaUsuario(id = 1, listName = "Ver más tarde", list = listOf())
    }
}