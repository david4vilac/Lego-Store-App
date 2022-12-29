package com.david4vilac.legostore

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.david4vilac.legostore.databinding.ActivityDetailProductBinding
import com.david4vilac.legostore.usecases.preferences.ProductPrefs.Companion.prefs

class DetailProduct : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val prefsUser: SharedPreferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)

        binding.tvNameProduct.text = "Nombre: ${prefs.getName()}"
        binding.tvPrice.text = "Precio: $${prefs.getPrice()}"
        binding.tvStock.text = "Stock: ${prefs.getStock()}"
        binding.tvDescription.text = "Descripción: ${prefs.getDescription()}"

        binding.emailTextView.text = prefsUser.getString("email", null)

        title = prefs.getName()
        Glide.with(this)
            .load(prefs.getImage())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.iVProductDetail)


        //binding.buttonBack.setOnClickListener {
            //startActivity(Intent(applicationContext, AuthActivity::class.java))
            //finish()
        //}
    }
}