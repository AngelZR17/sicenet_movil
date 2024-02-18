package com.brian_angel.sicenet

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.brian_angel.sicenet.ui.theme.SicenetMovilTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Cookie
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy
import java.net.HttpCookie
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SicenetMovilTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.fillMaxSize()){
                        Column(
                            modifier = Modifier,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            ContentHomeScreenUI()
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
fun ContentHomeScreenUI(){
    val currentNote = rememberSaveable { mutableStateOf("") }
    val currentTitle = rememberSaveable { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                title = { Text("SiceNet", color = Color.White) },
                navigationIcon = {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Botón de Retroceso", tint = Color.White)
                    }
                }
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = dimensionResource(R.dimen.padding_top_column), bottom = 80.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier . fillMaxWidth (),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        modifier = Modifier.fillMaxWidth()
                            .padding(
                                dimensionResource(R.dimen.padding_start_textField),
                                end = dimensionResource(R.dimen.padding_end_textField)
                            ),
                        value = currentTitle.value,
                        onValueChange = { value ->
                            currentTitle.value = value
                        },
                        label = { Text("Numero de control") }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(
                        modifier = Modifier.fillMaxWidth()
                            .padding(
                                dimensionResource(R.dimen.padding_start_textField),
                                end = dimensionResource(R.dimen.padding_end_textField)
                            ),
                        value = currentNote.value,
                        onValueChange = { value ->
                            currentNote.value = value
                        },
                        label = { Text("Contraseña") }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {

                            CoroutineScope(Dispatchers.Default).launch{
                                //getCookie()
                                enviarSoapRequest()
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Ingresar")
                    }
                }
            }
        },
        bottomBar = {

        }
    )
}

fun getCookie(){
    val url = URL("https://sicenet.surguanajuato.tecnm.mx/ws/wsalumnos.asmx")
    val connection = url.openConnection() as HttpURLConnection
    connection.requestMethod = "POST"
    connection.doInput = true
    connection.doOutput = true
    // Leer las cookies de la cabecera de la respuesta
    connection.setRequestProperty("Content-Type", "text/xml;charset=utf-8")
    connection.setRequestProperty("SOAPAction", "http://tempuri.org/accesoLogin")
    val cookiesHeader = connection.getHeaderField("Set-Cookie")
    // Si hay cookies
    if (cookiesHeader != null) {
        Log.d("Cookie", cookiesHeader)

    }
    connection.disconnect()
}


fun enviarSoapRequest() {
    val cookieManager = CookieManager()
    cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
    java.net.CookieHandler.setDefault(cookieManager)
    val soapEndpointUrl = "https://sicenet.itsur.edu.mx/ws/wsalumnos.asmx"
    val soapXml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <accesoLogin xmlns="http://tempuri.org/">
                  <strMatricula>S20120168</strMatricula>
                  <strContrasenia>d$9AE8r</strContrasenia>
                  <tipoUsuario>ALUMNO</tipoUsuario>
                </accesoLogin>
              </soap:Body>
            </soap:Envelope>
        """.trimIndent()
    val url = URL(soapEndpointUrl)
    val connection = url.openConnection() as HttpURLConnection
    connection.requestMethod = "POST"
    connection.setRequestProperty("Content-Type", "text/xml;charset=utf-8")
    connection.setRequestProperty("SOAPAction", "http://tempuri.org/accesoLogin")
    //connection.setRequestProperty("Cookie", "nmhxnn554ti2re55gexhoy45")
    connection.doOutput = true
    val cookies = cookieManager.cookieStore.cookies
    val outputStreamWriter = OutputStreamWriter(connection.outputStream)
    outputStreamWriter.write(soapXml)
    outputStreamWriter.flush()
    val responseCode = connection.responseCode
    if (responseCode == HttpURLConnection.HTTP_OK) {
        val cookiesHeader = connection.getHeaderField("Set-Cookie")
        Log.e("hi", cookies.size.toString())

        val inputStream = connection.inputStream
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val response = StringBuilder()
        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            response.append(line)
        }
        bufferedReader.close()
        Log.d("TAG", "$response")
    } else {

    }
    connection.disconnect()
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SicenetMovilTheme {
        Greeting("Android")
    }
}