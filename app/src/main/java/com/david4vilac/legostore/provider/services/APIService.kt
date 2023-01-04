package com.david4vilac.legostore.provider.services

import com.david4vilac.legostore.model.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {

    @GET("all-products/")
    fun getAllProducts(): Call<ProductResponse>

    @GET("detail/{id}")
    fun getDetailProduct(@Path("id") id: Int): Call<Product>

    @POST("buy")
    fun editAllProducts(): Call<ProductResponse>
}