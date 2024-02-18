package com.brian_angel.sicenet.network

import com.brian_angel.sicenet.model.AccessLoginResponse
import com.brian_angel.sicenet.model.SoapEnvelope
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SiceApiService {
    @Headers(
        "Content-Type: text/xml;charset=UTF-8",
        "Accept-Charset: UTF-8",
        "SOAPAction: \"http://tempuri.org/accesoLogin\""
    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun performSoapRequest(@Body envelope: SoapEnvelope): Call<AccessLoginResponse>
}