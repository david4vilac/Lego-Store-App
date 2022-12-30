package com.david4vilac.legostore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.david4vilac.legostore.databinding.ActivityShopBinding
import com.david4vilac.legostore.model.Product
import com.david4vilac.legostore.model.ProductList
import com.david4vilac.legostore.usecases.preferences.SaveTheme
import com.david4vilac.legostore.usecases.rows.ProductAdapter
import kotlinx.coroutines.processNextEventInCurrentThread

class ShopActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopBinding
    private lateinit var saveTheme: SaveTheme

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
    }

    override fun onStart() {
        initRecyclerView()
        super.onStart()
    }

    fun initRecyclerView(){
        val productList2 = ProductList.productShopList
        val rv = findViewById<RecyclerView>(R.id.rvShopCart)
        binding.rvShopCart.layoutManager = LinearLayoutManager(this)
        rv.adapter = ProductAdapter(productList2, HomeActivity())

        binding.textView.text = productList2.toString()

    }

    fun onClickDelete(item: Product) {
        val productList2 = ProductList.productShopList
        //val rv = findViewById(R.id.rvShopCart)
        //rv.adapter = ProductAdapter(productList2)
        //ProductAdapter(productList2).notifyItemRemoved(1)
    }
}