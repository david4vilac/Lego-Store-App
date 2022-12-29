package com.david4vilac.legostore.usecases.preferences
import android.content.Context

class Prefs(context: Context) {

    val SHARE_PREFERENCE = "Mydtb"
    val SHARE_ID = "ID"
    val SHARE_NAME = "NAME"
    val SHARE_PRICE = "PRICE"
    val SHARE_STOCK = "STOCK"
    val SHARE_IMAGE = "IMAGE"
    val SHARE_DESCRIPTION = "DESCRIPTION"

    val storage = context.getSharedPreferences(SHARE_PREFERENCE, 0)


    fun saveID(id:Int){
        storage.edit().putInt(SHARE_ID, id).apply()
    }

    fun getID():Int{
        return storage.getInt(SHARE_ID, 0)
    }

    fun saveName(name:String){
        storage.edit().putString(SHARE_NAME, name).apply()
    }

    fun getName():String{
        return storage.getString(SHARE_NAME, "")!!
    }

    fun savePrice(unit_price:Int){
        storage.edit().putInt(SHARE_PRICE, unit_price).apply()
    }

    fun getPrice():Int{
        return storage.getInt(SHARE_PRICE, 0)
    }

    fun saveStock(stock:Int){
        storage.edit().putInt(SHARE_STOCK, stock).apply()
    }

    fun getStock():Int{
        return storage.getInt(SHARE_STOCK, 0)
    }

    fun saveImage(image:String){
        storage.edit().putString(SHARE_IMAGE, image).apply()
    }

    fun getImage():String{
        return storage.getString(SHARE_IMAGE, "")!!
    }

    fun saveDescription(description:String){
        storage.edit().putString(SHARE_DESCRIPTION, description).apply()
    }

    fun getDescription():String{
        return storage.getString(SHARE_DESCRIPTION, "")!!
    }

    //Vaciar preferencias
    fun wipe(){
        storage.edit().clear().apply()
    }

}