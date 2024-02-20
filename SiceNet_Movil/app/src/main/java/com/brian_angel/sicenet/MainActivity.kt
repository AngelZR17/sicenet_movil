package com.brian_angel.sicenet

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.brian_angel.sicenet.ViewModel.viewModelLogin
import com.brian_angel.sicenet.navigation.AppNavigation
import com.brian_angel.sicenet.navigation.Screens
import com.brian_angel.sicenet.ui.theme.SicenetTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SicenetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AppNavigation()
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreenLogin(
    navController: NavController,
    viewmodel: viewModelLogin = viewModel(factory = viewModelLogin.Factory)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                title = { Text("SiceNet", color = MaterialTheme.colorScheme.onPrimary) },
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp, bottom = 80.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(40.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextField(
                            value = viewmodel.noControl,
                            label = { Text(text = "No. Control") },
                            onValueChange = {
                                viewmodel.updateMatricula(it)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.height(25.dp))
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextField(
                            value = viewmodel.contrasenia,
                            label = { Text(text = "Contraseña") },
                            onValueChange = {
                                viewmodel.updatePassword(it)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation(),
                        )
                    }
                    Spacer(modifier = Modifier.height(25.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val scope = rememberCoroutineScope()
                        Button(
                            onClick = {
                                if (validarDatosUser(viewmodel)) {
                                    scope.launch {
                                        if(obtenerAcceso(viewmodel)) {
                                            obtenerInfo(viewmodel,navController)
                                        }
                                    }
                                }
                            },
                        ) {
                            Text(text = "Ingresar")
                        }
                    }
                }
            }
        }
    )
}

fun validarDatosUser(viewmodel:viewModelLogin):Boolean{
    return !viewmodel.noControl.equals("") && !viewmodel.contrasenia.equals("")
}

suspend fun obtenerAcceso(viewmodel:viewModelLogin):Boolean{
    return viewmodel.getAccess(viewmodel.noControl, viewmodel.contrasenia)
}

suspend fun obtenerInfo(viewmodel:viewModelLogin,navController:NavController){
    var info = viewmodel.getInfo()
    var encodedInfo = Uri.encode(info)
    navController.navigate(route = Screens.Info.route + encodedInfo)
}
