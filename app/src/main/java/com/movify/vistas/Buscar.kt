package com.movify.vistas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.movify.R

@Composable
fun Buscar(){

    var valor: String by remember {mutableStateOf("")}

    val view = LocalView.current

    val label = stringResource(R.string.componente_busqueda_label)

    Column(Modifier.padding(8.dp)){
        BarraBusqueda(
            valor = valor,
            label = label,
            clickDone = { view.clearFocus() },
            buscar = {query->
                valor = query
                //todo llamar al view model para mostrar los resultados
            },
            limpiarBusqueda = {
                valor = ""
                view.clearFocus()
                //todo eliminar los resultados
            }
        )
    }


}

@Composable
fun BarraBusqueda(valor:String,label:String,clickDone:()->Unit,buscar:(String)->Unit,limpiarBusqueda:()->Unit){
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = valor,
        onValueChange = { buscar(it) },
        label = { Text(text = label) },
        textStyle = MaterialTheme.typography.subtitle1,
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = { limpiarBusqueda() }) {
                Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear")
            }
        },
        keyboardActions = KeyboardActions(onDone = { clickDone() }),
        keyboardOptions = KeyboardOptions(
            imeAction = androidx.compose.ui.text.input.ImeAction.Done,
            keyboardType = KeyboardType.Text
        )
    )
}