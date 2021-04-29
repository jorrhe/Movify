package com.movify.vistas

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.movify.R

sealed class Vista(val ruta: String, @StringRes val idEtiqueta: Int,val icono: ImageVector){

    object Inicio : Vista("inicio", R.string.inicio,Icons.Filled.Home)
    object Buscar : Vista("buscar", R.string.buscar,Icons.Filled.Search)
    object Perfil : Vista("perfil", R.string.perfil,Icons.Filled.Person)

    object InfoPelicula : Vista("pelicula", R.string.pelicula,Icons.Filled.Add)

}
