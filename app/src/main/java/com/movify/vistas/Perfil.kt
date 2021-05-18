package com.movify.vistas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.movify.database.InfoLista
import com.movify.ui.theme.MovifyTheme
import com.movify.utils.getIconoLista
import info.movito.themoviedbapi.model.MovieDb
import androidx.compose.material.Icon
import androidx.compose.ui.res.stringResource
import com.movify.R


@Composable
fun Perfil(
    listas:List<InfoLista>,
    verLista: (InfoLista) -> Unit,
    temaOscuro:Boolean,
    cambiarTema:()->Unit
){
    LazyColumn(
        modifier = Modifier.fillMaxHeight()
    ){

        item {
            Text(
                text = stringResource(R.string.componente_perfil_tus_listas),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(12.dp)
            )
        }

        items(listas){ lista ->
            CardLista(
                texto = lista.nombre,
                onClick = { verLista(lista) }
            ) {
                Icon(
                    painter = painterResource(getIconoLista(lista.idInfoLista)),
                    contentDescription = null
                )
            }
        }

        item {
            Text(
                text = stringResource(R.string.componente_perfil_ajustes),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(12.dp)
            )
        }

        item {

            var temaOscuroLocal by rememberSaveable { mutableStateOf(temaOscuro) }

            val textoTema = if(!temaOscuroLocal){
                stringResource(R.string.componente_perfil_modo_oscuro)
            }else{
                stringResource(R.string.componente_perfil_modo_claro)
            }

            Button(
                onClick = {
                    cambiarTema()
                    temaOscuroLocal = !temaOscuroLocal
                },
                modifier = Modifier.padding(12.dp)
            ) {
                Text(textoTema)
            }
        }

    }



}


@Composable
fun CardLista(texto:String,  onClick: ()-> Unit, content:@Composable ()-> Unit){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp, vertical = 5.dp)
        .clickable { onClick() },
        elevation = 10.dp,
    ){
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier= Modifier
                .padding(15.dp)
        ){
            Text(texto)
            content()
        }

    }
}

@Preview
@Composable
fun PreviewPerfil(){
    MovifyTheme {

        Perfil(
            listas = listOf(
                InfoLista(1,"Favoritos",""),
                InfoLista(2,"Vistas",""),
                InfoLista(3,"Ver más tarde","")
            ),
            verLista = {},
            cambiarTema = {},
            temaOscuro = false
        )
    }
}

