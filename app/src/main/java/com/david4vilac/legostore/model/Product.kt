package com.david4vilac.legostore.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("unit_price") val unit_price: Int,
    @SerializedName("stock") val stock: Int,
    @SerializedName("image") val image: String,
    )
