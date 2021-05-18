package com.movify.componentes

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun BarraSuperior(titulo:String){

    TopAppBar(
        title = {
            Text(text = titulo)
        }
    )

}