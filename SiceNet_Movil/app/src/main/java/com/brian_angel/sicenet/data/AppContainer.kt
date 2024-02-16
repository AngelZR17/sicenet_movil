package com.brian_angel.sicenet.data

import com.brian_angel.sicenet.network.SiceApiService
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

interface AppContainer {
    val siceNetRepository: SiceNetRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "http://sicenet.surguanajuato.tecnm.mx"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build()

    private val retrofitService: SiceApiService by lazy {
        retrofit.create(SiceApiService::class.java)
    }

    override val siceNetRepository: SiceNetRepository by lazy {
        NetworkSiceNetRepository(retrofitService)
    }
}