package com.david4vilac.legostore

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.david4vilac.legostore.databinding.ActivityShopBinding
import com.david4vilac.legostore.model.Product
import com.david4vilac.legostore.model.ProductList
import com.david4vilac.legostore.provider.preferences.ProductPrefs
import com.david4vilac.legostore.provider.preferences.SaveTheme
import com.david4vilac.legostore.usecases.rows.ShopAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth


class ShopActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopBinding
    private lateinit var saveTheme: SaveTheme
    private lateinit var adapter: ShopAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        saveTheme = SaveTheme(this)
        if(saveTheme.loadDarkModeState() == true){
            setTheme(R.style.Theme_LegoStore)
        }else{
            setTheme(R.style.ThemeLegoDark)
        }
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        setup()

    }

    override fun onStart() {
        initRecyclerView()
        super.onStart()
    }

    fun setup(){
        val logOutBtn: FloatingActionButton = findViewById(R.id.logOutBtnShopping)
        val btnBuy: Button = findViewById(R.id.btnBuy)
        //editAllProducts
        btnBuy.setOnClickListener {
            val homeActivity = HomeActivity()
            homeActivity.editAllProducts()
        }

        logOutBtn.setOnClickListener {
            //val prefsUser: SharedPreferences.Editor = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            //prefsUser.clear()
            //prefsUser.apply()
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, AuthActivity::class.java))
            //onBackPressed()
            ProductPrefs.prefs.wipe()
            finish()
        }
        preferencesItem()
    }

    fun initRecyclerView(){
        val productList2 = ProductList.productShopList
        adapter = ShopAdapter(
            products = productList2,
            onClickListener = { product -> onItemSelected(product)},
            onClickDelete = {position -> onClickDelete(position)}
        )

        binding.rvShopCart.layoutManager = LinearLayoutManager(this)
        binding.rvShopCart.adapter = adapter
    }

    private fun onItemSelected(product: Product) {
        Toast.makeText(this, product.name, Toast.LENGTH_SHORT).show()
    }

    private fun onClickDelete(position: Int){
        ProductList.productShopList.removeAt(position)
        adapter.notifyItemRemoved(position)
        preferencesItem()
    }

    private fun preferencesItem(){
        val tvProgressTwo: TextView = findViewById(R.id.tvProgressTwo)
        val emailTextView: TextView = findViewById(R.id.emailTextView)

        val prefsUser: SharedPreferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefsUser.getString("email", null)

        emailTextView.text = email
        tvProgressTwo.text = ProductList.productShopList.size.toString()
    }


}