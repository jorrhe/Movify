package com.movify.vistas


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextOverflow

val paddingLeft = 16.dp

@Composable
fun InfoPelicula(
    pelicula: MovieDb,

    listasGuardadas:List<InfoLista>,
    agregadaALista:HashMap<Long,Boolean>,
    accionBotonLista:(Long, MovieDb)->Unit,

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

        /*if(pelicula.genres!=null)
            Text(text = pelicula.genres.toString())*/


        DatosPelicula(
            pelicula = pelicula,
            listasGuardadas = listasGuardadas,
            agregadaALista = agregadaALista,
            accionBotonLista = accionBotonLista
        )

        ServiciosStreaming(
            serviciosStreaming = imagenesServicios
        )

        PeliculasRelacionadas(
            peliculas = peliculasRelacionadas,
            cargarPelicula = cargarPelicula
        )


    }

}

@Composable
fun HeaderCaratula(pelicula: MovieDb){

    Box {
            val painter = rememberCoilPainter(
                request = getUrlBackdrop(pelicula.backdropPath?:""),
                fadeIn = true
            )

            Image(
                painter = painter,
                contentDescription = "pelicula.title",
                modifier = Modifier
                    .aspectRatio(1.7f)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )

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

@Composable
fun DatosPelicula(
    pelicula: MovieDb,
    listasGuardadas:List<InfoLista>,
    agregadaALista:HashMap<Long,Boolean>,
    accionBotonLista:(Long,MovieDb)->Unit
){
    var textoExpandido = rememberSaveable {mutableStateOf(false)}

    Column(
        Modifier.padding(paddingLeft,10.dp)
    ){

        ClickableText(
            text = AnnotatedString(
                if (pelicula.overview.isNullOrBlank()) {
                    stringResource(id = R.string.description_not_found)
                }
                else {
                    pelicula.overview
                },

                paragraphStyles= listOf(
                    AnnotatedString.Range<ParagraphStyle>(ParagraphStyle(textAlign = TextAlign.Justify),0, pelicula.overview?.length?: 0)
                ),
                spanStyles = listOf(AnnotatedString.Range<SpanStyle>(SpanStyle(color = Color.White), 0, pelicula.overview?.length?:0))
            )
            ,
            //textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.body2,
            maxLines = if (!textoExpandido.value) 6 else{ 20 },
            overflow = TextOverflow.Ellipsis,
            onClick = {
                textoExpandido.value = !textoExpandido.value
            }

        )

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 10.dp)
        ){

            listasGuardadas.forEach {lista->

                var colorear:Boolean by rememberSaveable { mutableStateOf(agregadaALista[lista.idInfoLista]?:false) }

                var color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)

                if (colorear){
                    color = Verde200
                }

                IconButton(
                    onClick = {
                        colorear = !colorear
                        accionBotonLista(lista.idInfoLista,pelicula)
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Column {
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
                .padding(paddingLeft,0.dp, paddingLeft,20.dp)
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
                        .clip(CircleShape)

                )
            }

        }

    }
}

@Composable
fun PeliculasRelacionadas(
    peliculas:List<MovieDb>?,
    cargarPelicula:(MovieDb)->Unit
){

    if(peliculas!=null && peliculas.isNotEmpty()){

        Text(
            text = stringResource(R.string.componente_relacionadas_label),
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .padding(paddingLeft,20.dp)
        )

        LazyRow() {
            items(items = peliculas){pelicula->
                CaratulaPelicula(
                    pelicula = pelicula,
                    cargarPelicula = cargarPelicula,
                    modifier = Modifier
                        .width(138.dp)
                )
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
    //DatosPelicula(pelicula = pelicula, listasGuardadas = listas, agregarALista=null)
}
