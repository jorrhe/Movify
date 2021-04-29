package com.movify.componentes

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun MensajeError(mensaje:String){
    Text(text = mensaje)
}