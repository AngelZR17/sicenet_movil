package com.brian_angel.sicenet.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.brian_angel.sicenet.AlumnosContainer
import com.brian_angel.sicenet.data.AlumnosRepository
import kotlinx.coroutines.async

class viewModelLogin(private val alumnosRepository: AlumnosRepository):ViewModel(){
    var noControl by mutableStateOf("")
    var contrasenia by mutableStateOf("")

    fun updateMatricula(string: String){
        noControl=string
    }
    fun updatePassword(string: String){
        contrasenia=string
    }

    suspend fun getAccess(noControl: String, contrasenia: String): Boolean {
        return alumnosRepository.obtenerAcceso(noControl, contrasenia)
    }

    suspend fun getInfo():String{
        val informacion = viewModelScope.async {
            alumnosRepository.obtenerInfo()
        }
        return informacion.await()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AlumnosContainer)
                val alumnosAplication = application.container.alumnosRepository
                viewModelLogin(alumnosRepository = alumnosAplication)
            }
        }
    }
}

