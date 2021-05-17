package com.movify

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import com.movify.componentes.BotonError
import com.movify.componentes.ErrorInicio
import com.movify.componentes.MensajeError
import com.movify.repositorios.ListRepository
import com.movify.repositorios.PeliculaRepositorio
import com.movify.utils.ConnectionLiveData
import com.movify.viewmodels.*

class MainActivity : ComponentActivity() {

    val repositorioApi = PeliculaRepositorio()

    val inicioViewModel:InicioPeliculaViewModel by viewModels{InicioPeliculaViewModelFactory(repositorioApi)}
    val infoPeliculaViewModel:InfoPeliculaViewModel by viewModels{ InfoPeliculaViewModelFactory(repositorioApi) }
    val searchViewModel:SearchViewModel by viewModels {SearchViewModelFactory(repositorioApi)}

    lateinit var movieListsViewModel:MovieListsViewModel
    lateinit var detailedListViewModel:DetailedListViewModel

    lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isInternetAvailable()) {
            setContent {
                ErrorInicio(mensaje = stringResource(R.string.error_sin_conexion_internet)) { init() }
            }
        }

        init()
    }

    private fun init() {
        if(isInternetAvailable()) {
            val repositorioListas = ListRepository(this.application)

            val movieListsViewModel: MovieListsViewModel by viewModels {
                MovieListsViewModelFactory(
                    repositorioListas
                )
            }
            this.movieListsViewModel = movieListsViewModel

            val detailedListViewModel: DetailedListViewModel by viewModels {
                DetailedListViewModelFactory(
                    repositorioListas,
                    repositorioApi
                )
            }
            this.detailedListViewModel = detailedListViewModel

            connectionLiveData = ConnectionLiveData(this@MainActivity)

            setContent {
                val isNetworkAvailable = connectionLiveData.observeAsState(false).value
                MovifyApp(
                    inicioViewModel,
                    infoPeliculaViewModel,
                    searchViewModel,
                    movieListsViewModel,
                    detailedListViewModel,
                    isNetworkAvailable
                )
            }
        }
    }

    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            this@MainActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }
}