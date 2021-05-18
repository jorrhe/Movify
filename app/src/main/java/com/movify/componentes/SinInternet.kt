package com.movify.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SinInternet(reintentar:()->Unit){

    Row(
            verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "No hay conexión a internet",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Button(onClick = reintentar) {
                Text(text = "Volver a cargar")
            }
        }


    }

}

@Preview
@Composable
fun PreviewSinInternet(){
    MaterialTheme {
        SinInternet {

        }
    }
}