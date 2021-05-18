package com.movify.componentes

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigate
import com.movify.ui.theme.AzulMovify
import com.movify.vistas.Vista

@Composable
fun BarraInferior(navController: NavController) {

    val items = listOf(
        Vista.Inicio,
        Vista.Buscar,
        Vista.Perfil
    )

    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
        items.forEach { vista ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector=vista.icono,
                        contentDescription = vista.ruta
                    )
                },
                label = {
                    Text(
                        text = stringResource(vista.idEtiqueta)
                    )
                },
                selected = currentRoute == vista.ruta,
                onClick = {
                    navController.navigate(vista.ruta) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo = navController.graph.startDestination
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                    }
                }
            )
        }
    }


}