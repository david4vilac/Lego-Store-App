package com.david4vilac.legostore

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.david4vilac.legostore.databinding.ActivityAuthBinding
import com.david4vilac.legostore.model.Product
import com.david4vilac.legostore.network.APIService
import com.david4vilac.legostore.usecases.rows.ProductAdapter
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()

        bundle.putString("message", "Integración con FB completa")
        analytics.logEvent("InitScreem", bundle)



        // Setup
        setup()
        session()


    }

    private fun session(){
        val prefsUser: SharedPreferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefsUser.getString("email", null)
        if(email != null ){
            showHome(email, ProviderType.BASIC)
        }
    }

    private fun setup(){
        title = "Autenticación"


        val signUpButton: Button = findViewById(R.id.signUpBtn)
        val logInBtn: Button = findViewById(R.id.logInBtn)

        val editTextEmail: EditText = findViewById(R.id.editTextEmail)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)

        signUpButton.setOnClickListener {
            if(editTextEmail.text.isNotEmpty() && passwordEditText.text.isNotEmpty()  ){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(editTextEmail.text.toString(),
                    passwordEditText.text.toString()).addOnCompleteListener {
                        if(it.isSuccessful){
                            showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                        }else{
                            showAlert()
                        }
                }
            }
        }

        logInBtn.setOnClickListener {
            if(editTextEmail.text.isNotEmpty() && passwordEditText.text.isNotEmpty()  ){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(editTextEmail.text.toString(),
                    passwordEditText.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                    }else{
                        showAlert()
                    }
                }
            }

        }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: ProviderType){
        val homeIntent = Intent(this, HomeActivity::class.java).apply{
            putExtra("email", email)
            putExtra("provider", provider)
        }
        startActivity(homeIntent)
        finish()
    }

}