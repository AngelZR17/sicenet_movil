package com.brian_angel.sicenet.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.brian_angel.sicenet.HomeScreenLogin
import com.brian_angel.sicenet.showInfo

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.Login.route,
    ){
        composable(route = Screens.Login.route){
            HomeScreenLogin(navController)
        }
        composable(route = Screens.Info.route+"{text}",
            arguments = listOf(navArgument(name = "text"){
                type= NavType.StringType
            })){
            showInfo(navController,it.arguments?.getString("text"))
        }
    }
}