package com.brian_angel.sicenet.data

import com.brian_angel.sicenet.modelos.AlumnoLogin
import com.brian_angel.sicenet.modelos.InfoAlumno
import com.brian_angel.sicenet.network.InfoService
import com.brian_angel.sicenet.network.SiceApiService
import com.google.gson.Gson
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

interface AlumnosRepository {
    suspend fun obtenerAcceso(noControl: String, contrasenia: String):Boolean
    suspend fun obtenerInfo():String
}

class NetworkAlumnosRepository(
    private val alumnoApiService: SiceApiService,
    private val infoService: InfoService
): AlumnosRepository {
    override suspend fun obtenerAcceso(noControl: String, contrasenia: String ):Boolean{
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <accesoLogin xmlns="http://tempuri.org/">
                  <strMatricula>${noControl}</strMatricula>
                  <strContrasenia>${contrasenia}</strContrasenia>
                  <tipoUsuario>ALUMNO</tipoUsuario>
                </accesoLogin>
              </soap:Body>
            </soap:Envelope>
            """.trimIndent()
        val requestBody=xml.toRequestBody()
        alumnoApiService.obtenerCookies()
        try {
            var respuesta=alumnoApiService.obtenerAcceso(requestBody)
                .string().split("{","}")
            if(respuesta.size>1){
                val result = Gson().fromJson("{"+respuesta[1]+"}",
                    AlumnoLogin::class.java)
                return result.acceso.equals("true")
            } else
                return false
        }catch (e:IOException){
            return false
        }
    }

    override suspend fun obtenerInfo():String{
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getAlumnoAcademicoWithLineamiento xmlns="http://tempuri.org/" />
              </soap:Body>
            </soap:Envelope>
            """.trimIndent()
        val requestBody=xml.toRequestBody()
        try {
            val respuesta=infoService.getInfo(requestBody).
            string().split("{","}")
            if(respuesta.size>1){
                val result = Gson().fromJson("{"+respuesta[1]+"}",
                    InfoAlumno::class.java)
               return ""+result
            } else
               return ""
        }catch (e:IOException){
            return ""
        }
    }
}





