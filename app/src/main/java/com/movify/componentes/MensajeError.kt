package com.movify.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.movify.R
import com.movify.ui.theme.MovifyTheme

@Composable
fun MensajeError(mensaje:String){
    Column(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = mensaje,
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BotonError(click: ()->Unit){
    Column(modifier = Modifier
        .padding(30.dp)
        .fillMaxWidth()
        .wrapContentSize(Alignment.Center)) {
        Button(
            onClick = { click() }, colors = ButtonDefaults.textButtonColors(
                backgroundColor = colorResource(id = R.color.verde_200)
            )
        ) {
            Text(
                text = stringResource(id = R.string.reload),
                color = Color.White
            )
        }
    }
}

@Composable
fun ErrorInicio(mensaje: String, click: ()->Unit){
    MovifyTheme {
        Column(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            MensajeError(mensaje = mensaje)
            BotonError { click() }
        }
    }
}