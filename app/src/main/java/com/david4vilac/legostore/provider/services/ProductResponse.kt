package com.david4vilac.legostore.provider.services

import com.david4vilac.legostore.model.Product
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("products") val products: List<Product>
)
