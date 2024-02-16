package com.brian_angel.sicenet.data

import com.brian_angel.sicenet.network.SiceApiService

interface SiceNetRepository {
    //suspend fun getMarsPhotos(): List<MarsPhoto>
}

class NetworkSiceNetRepository(
    private val siceApiService: SiceApiService
) : SiceNetRepository {

    //override suspend fun getMarsPhotos(): List<MarsPhoto> = marsApiService.getPhotos()
}