package com.brian_angel.sicenet.navigation

sealed class Screens(val route:String) {
    object Login: Screens("Login")
    object Info: Screens("Info")
}