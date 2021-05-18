package com.movify.vistas


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.isFinalState
import com.movify.R
import com.movify.database.InfoLista
import com.movify.ui.theme.Verde200
import com.movify.utils.getIconoLista
import com.movify.utils.getUrlBackdrop
import info.movito.themoviedbapi.model.MovieDb
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.movify.componentes.SimpleFlowRow
import com.movify.ui.theme.AzulMovify
import com.movify.ui.theme.NegroTransparente
import com.movify.ui.theme.VerdeMovify
import info.movito.themoviedbapi.model.Genre

val paddingLeft = 16.dp

@Composable
fun InfoPelicula(
    pelicula: MovieDb,

    listasGuardadas:List<InfoLista>,
    agregadaALista:HashMap<Long,Boolean>,
    accionBotonLista:(Long, MovieDb)->Unit,
    borrarDeLista:(Long)->Unit,

    imagenesServicios: List<String>,

    peliculasRelacionadas: List<MovieDb>,
    cargarPelicula:(MovieDb)->Unit
){

    Column (
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {

        HeaderCaratula(
            pelicula = pelicula
        )

        DatosPelicula(
            pelicula = pelicula,
            listasGuardadas = listasGuardadas,
            agregadaALista = agregadaALista,
            accionBotonLista = accionBotonLista,
            borrarDeLista = borrarDeLista
        )

        ServiciosStreaming(
            serviciosStreaming = imagenesServicios
        )

        PeliculasRelacionadas(
            pelicula = pelicula,
            peliculas = peliculasRelacionadas,
            cargarPelicula = cargarPelicula
        )


    }

}

@Composable
fun HeaderCaratula(pelicula: MovieDb){

    Box {
        if (pelicula.backdropPath.isNullOrBlank()) {

            Text(
                text = pelicula.title,
                style = MaterialTheme.typography.h5,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()

            )

        } else {

            val painter = rememberCoilPainter(
                request = getUrlBackdrop(pelicula),
                fadeIn = true
            )


            Image(
                painter = painter,
                contentDescription = pelicula.title,
                modifier = Modifier
                    .aspectRatio(1.7f)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )

            if (painter.loadState.isFinalState()) {

                Spacer(
                    modifier = Modifier
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black),
                                0f,
                                200f
                            )
                        )
                        .fillMaxWidth()
                        .height(50.dp)
                        .align(Alignment.BottomCenter)
                )


                Text(
                    text = pelicula.title,
                    style = MaterialTheme.typography.h5,
                    color = Color.White,
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.BottomCenter)
                )

            }


        }
    }

}

@Composable
fun DatosPelicula(
    pelicula: MovieDb,
    listasGuardadas:List<InfoLista>,
    agregadaALista:HashMap<Long,Boolean>,
    accionBotonLista:(Long,MovieDb)->Unit,
    borrarDeLista:(Long)->Unit
){

    Column(
        Modifier.padding(paddingLeft,10.dp, paddingLeft, 0.dp)
    ){

        if(pelicula.genres != null && pelicula.genres.size>0){

            SimpleFlowRow(
                verticalGap = 8.dp,
                horizontalGap = 8.dp,
                alignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
            ) {

                val gradiente = remember { Brush.horizontalGradient(listOf(Color(0xFF28D8A3), Color(0xFF00BEB2))) }

                pelicula.genres.forEach { genero->
                    Etiqueta(
                        texto = genero.name,
                        gradiente = gradiente
                    )
                }
            }
        }

        Text(
            text = if (pelicula.overview.isNullOrBlank()) {
                ""
            } else {
                pelicula.overview
            },
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.body2,
        )

        Card(
            elevation = 5.dp,
            modifier = Modifier
                .padding(0.dp,10.dp)
        ) {
            Acciones(
                pelicula = pelicula,
                listasGuardadas = listasGuardadas,
                agregadaALista = agregadaALista,
                accionBotonLista = accionBotonLista,
                borrarDeLista = borrarDeLista
            )
        }

    }



}

@Composable
fun ServiciosStreaming(
    serviciosStreaming: List<String>
){

    if(serviciosStreaming.isNotEmpty()){
        Text(
            text = stringResource(R.string.componente_streaming_label),
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .padding(paddingLeft,10.dp, paddingLeft,20.dp)
        )

        LazyRow {

            items(items = serviciosStreaming){servicioImagen ->
                Image(
                    painter = rememberCoilPainter(
                        request = servicioImagen,
                        fadeIn = true
                    ),
                    contentDescription = servicioImagen,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(60.dp)
                        .padding(paddingLeft, 0.dp)
                        .border(1.dp, NegroTransparente, CircleShape)
                        .clip(CircleShape)

                )
            }

        }

    }
}

@Composable
fun PeliculasRelacionadas(
    pelicula:MovieDb,
    peliculas:List<MovieDb>?,
    cargarPelicula:(MovieDb)->Unit
){

    if(peliculas!=null){

        Text(
            text = stringResource(R.string.componente_relacionadas_label),
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .padding(paddingLeft,20.dp, paddingLeft,20.dp)
        )

        LazyRow() {
            items(items = peliculas){pelicula->
                CaratulaPelicula(
                    pelicula = pelicula,
                    cargarPelicula = {peliculaACargar->
                        if(pelicula.id!=peliculaACargar.id){
                            cargarPelicula(peliculaACargar)
                        }
                    },
                    modifier = Modifier
                        .width(138.dp)
                )
            }
        }
    }

}

/*ELEMENTOS SIMPLES*/
@Composable
fun Etiqueta(texto:String,gradiente:Brush,colorTexto:Color = NegroTransparente){



    Text(
        text = texto,
        modifier = Modifier
            .background(gradiente, RoundedCornerShape(4.dp))
            .padding(8.dp),
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        color = colorTexto,
        style = TextStyle(
            fontWeight = FontWeight.Bold
        )
    )
}

@Composable
fun Acciones(
    pelicula: MovieDb,
    listasGuardadas:List<InfoLista>,
    agregadaALista:HashMap<Long,Boolean>,
    accionBotonLista:(Long,MovieDb)->Unit,
    borrarDeLista:(Long)->Unit
){
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 10.dp)
    ){


        listasGuardadas.forEach {lista->

            var colorear:Boolean by rememberSaveable { mutableStateOf(agregadaALista[lista.idInfoLista]?:false) }

            var color = if(colorear){
                if(MaterialTheme.colors.isLight) AzulMovify else VerdeMovify
            }else {
                LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
            }

            IconButton(
                onClick = {
                    colorear = !colorear
                    accionBotonLista(lista.idInfoLista,pelicula)

                    if(!colorear){
                        borrarDeLista(lista.idInfoLista)
                    }

                },
                modifier = Modifier.weight(1f)
            ) {
                Column{

                    Icon(
                        painterResource(id = getIconoLista(lista.idInfoLista)),
                        contentDescription = lista.nombre,
                        tint = color,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text=lista.nombre,
                        textAlign = TextAlign.Center,
                        color = color,
                        style = MaterialTheme.typography.caption
                    )
                }

            }
        }

    }
}

@Composable
@Preview
fun DatosPeliculaPreview(){
    val pelicula = MovieDb()
    pelicula.overview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    val listas = listOf(InfoLista(1,"Favoritos",""),InfoLista(1,"Vistas",""),InfoLista(1,"Ver más tarde",""))

    val g1 = Genre()
    g1.name = "Acción"
    val g2 = Genre()
    g1.name = "Aventura"
    val g3 = Genre()
    g1.name = "Suspense"
    val g4 = Genre()
    g1.name = "Bélica"


    pelicula.genres = listOf(g1,g2,g3,g4)

    //DatosPelicula(pelicula = pelicula, listasGuardadas = listas,agregadaALista = HashMap(),accionBotonLista = {_->return true})
}
