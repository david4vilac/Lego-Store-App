package com.david4vilac.legostore.model

import android.util.Log

object ProductList {
    val productShopList = mutableListOf<Product>()
    val productListInit = mutableListOf<Product>()


    fun setProductShopList():List<Product>{
        return productShopList
    }
    fun shoppingCartList(product: Product){
        productShopList.add(product)
        Log.d("Agegando....", "${productShopList}")
    }

    fun shoppingRemoveItem(product: Product){
        productShopList.remove(product)
        Log.d("Removiendo....", "${productShopList}")
    }
}