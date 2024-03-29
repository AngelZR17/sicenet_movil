package com.brian_angel.sicenet.data

import com.brian_angel.sicenet.network.InfoService
import com.brian_angel.sicenet.network.SiceApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

interface AppContainer{
    val alumnosRepository: AlumnosRepository
}
class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://sicenet.surguanajuato.tecnm.mx/"
    private val interceptor= CookiesInterceptor()
    private val cliente = OkHttpClient.Builder().addInterceptor(interceptor).build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .baseUrl(BASE_URL).client(cliente)
        .build()

    private val retrofitService : SiceApiService by lazy {
        retrofit.create(SiceApiService::class.java)
    }

    private val retrofitServiceInfo : InfoService by lazy {
        retrofit.create(InfoService::class.java)
    }

    override val alumnosRepository: AlumnosRepository by lazy {
        NetworkAlumnosRepository(retrofitService,retrofitServiceInfo)
    }
}
class CookiesInterceptor : Interceptor {
    private var cookies: List<String> = emptyList()
    fun setCookies(cookies: List<String>) {
        this.cookies = cookies
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (cookies.isNotEmpty()) {
            val cookiesHeader = StringBuilder()
            for (cookie in cookies) {
                if (cookiesHeader.isNotEmpty()) {
                    cookiesHeader.append("; ")
                }
                cookiesHeader.append(cookie)
            }
            request = request.newBuilder()
                .header("Cookie", cookiesHeader.toString())
                .build()
        }
        val response = chain.proceed(request)
        val receivedCookies = response.headers("Set-Cookie")
        if (receivedCookies.isNotEmpty()) {
            setCookies(receivedCookies)
        }
        return response
    }
}