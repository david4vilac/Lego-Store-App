package com.david4vilac.legostore

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.david4vilac.legostore.databinding.ActivityDetailProductBinding
import com.david4vilac.legostore.model.Product
import com.david4vilac.legostore.model.ProductList
import com.david4vilac.legostore.usecases.preferences.ProductPrefs.Companion.prefs
import com.david4vilac.legostore.usecases.preferences.SaveTheme
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class DetailProduct : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding
    private lateinit var saveTheme: SaveTheme

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        saveTheme = SaveTheme(this)
        if(saveTheme.loadDarkModeState() == true){
            setTheme(R.style.Theme_LegoStore)
        }else{
            setTheme(R.style.ThemeLegoDark)
        }

        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val prefsUser: SharedPreferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val logOutBtn: FloatingActionButton = findViewById(R.id.logOutBtnDetails)

        binding.tvNameProduct.text = prefs.getName()
        binding.tvPrice.text = "Precio: $${prefs.getPrice()}"
        binding.tvStock.text = "Stock: ${prefs.getStock()}"
        binding.tvDescription.text = "Descripción: ${prefs.getDescription()}"



        binding.emailTextView.text = prefsUser.getString("email", null)

        title = prefs.getName()
        Glide.with(this)
            .load(prefs.getImage())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.iVProductDetail)

        binding.btnAddShopping.setOnClickListener {

            val product = Product(0, prefs.getName(),
                prefs.getPrice(),prefs.getStock(),
                prefs.getImage(),"")
            ProductList.productShopList.add(product)
            preferencesItem()
        }

        binding.btnPay2.setOnClickListener {
            val ShopIntent = Intent(this, ShopActivity::class.java)
            startActivity(ShopIntent)
        }

        logOutBtn.setOnClickListener {
            //val prefsUser: SharedPreferences.Editor = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            //prefsUser.clear()
            //prefsUser.apply()
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, AuthActivity::class.java))
            //onBackPressed()
            prefs.wipe()
            finish()
        }
        preferencesItem()
    }

    private fun preferencesItem(){
        val tvProgressTwo: TextView = findViewById(R.id.tvProgressTwo)
        tvProgressTwo.text = ProductList.productShopList.size.toString()
    }
}