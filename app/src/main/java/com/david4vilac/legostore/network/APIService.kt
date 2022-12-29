package com.david4vilac.legostore.network

import com.david4vilac.legostore.model.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface APIService {

    @GET("all-products/")
    fun getAllProducts(): Call<ProductResponse>

    @GET("detail/{id}")
    fun getDetailProduct(@Path("id") id: Int): Call<Product>
}