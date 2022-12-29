package com.david4vilac.legostore.network

import retrofit2.http.GET

interface APIService {

    @GET
    fun getAllProducts(){

    }
}