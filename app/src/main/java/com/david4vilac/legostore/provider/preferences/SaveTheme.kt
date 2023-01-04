package com.david4vilac.legostore.provider.preferences

import android.content.SharedPreferences
import android.content.Context

class SaveTheme(context: Context) {
    private var sharedPreferences: SharedPreferences = context.getSharedPreferences("file",Context.MODE_PRIVATE)


    //This method will save the nigth mode state TRUE or FALSE
    fun setDarkModeState(state: Boolean?){
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean("Dark", state!!)
        editor.apply()
    }

    //This method will load the night mode state
    fun loadDarkModeState(): Boolean? {
        return sharedPreferences.getBoolean("Dark", false)
    }

}