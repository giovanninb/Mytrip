package com.example.appviagens.telas

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.appviagens.ScreenManager

@Composable
fun Controle() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreenManager.Login.route) {

        composable(ScreenManager.Login.route) {
            EstadoLogin(navController = navController)
        }

        composable(ScreenManager.Cadastro.route) {
            CadastroCompose(navController = navController)
        }

        composable("home/{nameUser}/{idUser}",
        arguments = listOf(
            navArgument("nameUser") {
                type = NavType.StringType
            },
            navArgument("idUser"){
                type = NavType.IntType
            }
        )
        ) {
            val nameUser = it.arguments?.getString("nameUser")
            val idUser = it.arguments?.getInt("idUser")
            if(nameUser != null && idUser != null){
                HomeNavigation(nameUser,idUser)
            }
        }
    }
}