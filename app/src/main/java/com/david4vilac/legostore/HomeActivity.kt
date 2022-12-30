package com.david4vilac.legostore

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.david4vilac.legostore.databinding.ActivityHomeBinding
import com.david4vilac.legostore.model.Product
import com.david4vilac.legostore.model.ProductList
import com.david4vilac.legostore.network.APIService
import com.david4vilac.legostore.usecases.preferences.ProductPrefs.Companion.prefs
import com.david4vilac.legostore.usecases.preferences.SaveTheme
import com.david4vilac.legostore.usecases.rows.ProductAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

enum class ProviderType {
    BASIC
}

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: ProductAdapter
    private val productList = mutableListOf(Product(9,"Prueba", 7, 8,"https://as01.epimg.net/meristation/imagenes/2022/04/24/reportajes/1650777901_909318_1650786230_noticia_normal.jpg",""))
    private val productShopList = mutableListOf<Product>()
    private lateinit var saveTheme: SaveTheme


    override fun onCreate(savedInstanceState: Bundle?) {
        saveTheme = SaveTheme(this)
        if(saveTheme.loadDarkModeState() == true){
            setTheme(R.style.Theme_LegoStore)
        }else{
            setTheme(R.style.ThemeLegoDark)
        }

        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val bundle:Bundle? = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email ?: "", provider ?: "")

        //Persistencia de datos

        val prefsUser: SharedPreferences.Editor = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefsUser.putString("email", email)
        prefsUser.apply()

        initRecyclerView()
        getAllProducts()

    }

    fun initRecyclerView(){
        val rv = findViewById<RecyclerView>(R.id.rvProducts)
        rv.adapter = ProductAdapter(productList, this)
        rv.layoutManager = LinearLayoutManager(this)
        //adapter = ProductAdapter(productList)
        //binding.rvProducts.layoutManager = LinearLayoutManager(this)

        rv.setOnClickListener {
            getProduct()
            Log.d("Respuesta", "Probando click" )
        }

    }


    private fun setup(email: String, provider: String){
        title = "Inicio"

        val logOutBtn: FloatingActionButton = findViewById(R.id.logOutBtn)
        val btnPay: ImageButton = findViewById(R.id.btnPay)

        val emailTextView: TextView = findViewById(R.id.emailTextView)
        val provierTextView: TextView = findViewById(R.id.provierTextView)

        emailTextView.text = email
        provierTextView.text = provider

        btnPay.setOnClickListener {
            val ShopIntent = Intent(this, ShopActivity::class.java)
            startActivity(ShopIntent)
        }

        logOutBtn.setOnClickListener {

            val prefsUser: SharedPreferences.Editor = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefsUser.clear()
            prefsUser.apply()
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, AuthActivity::class.java))
            //onBackPressed()
            prefs.wipe()
            finish()
        }
    }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://677e3cc8-7e90-4f17-a8cc-54c07fc50e40.mock.pstmn.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getAllProducts(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit()
                .create(APIService::class.java).getAllProducts().execute()
            val response = call.body()
            Log.d("Respuesta", response?.products.toString())
            runOnUiThread{
                if(call.isSuccessful){
                    val productList3 = response?.products ?: emptyList()
                    productList.clear()
                    productList.addAll(productList3)
                    initRecyclerView()
                }
            }
        }
    }

    fun searchByName(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit()
                .create(APIService::class.java).getDetailProduct(id).execute()
            val response = call.body()
            runOnUiThread{
                if(call.isSuccessful){
                    val name = response?.name ?: ""
                    val image = response?.image ?: ""
                    val description = response?.description ?: ""
                    val unit_price = response?.unit_price ?: 0
                    val stock = response?.stock ?: 0
                    prefs.apply {
                        saveName(name)
                        saveImage(image)
                        saveDescription(description)
                        savePrice(unit_price)
                        saveStock(stock)
                    }
                    getProduct()
                }
            }
        }
    }

    fun sum(){
        val tvProgressShop: TextView = findViewById(R.id.tvProgressShop)
        tvProgressShop.text = ProductList.productShopList.size.toString()
    }

    fun getProduct(){
        val DetailProduct = Intent(this, DetailProduct::class.java).apply{
        }
        startActivity(DetailProduct)
        //finish()
    }

}