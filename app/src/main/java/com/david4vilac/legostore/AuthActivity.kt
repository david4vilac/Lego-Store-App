package com.david4vilac.legostore

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.david4vilac.legostore.databinding.ActivityAuthBinding
import com.david4vilac.legostore.usecases.preferences.SaveTheme
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth


class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var saveTheme: SaveTheme
    private var switch : Switch? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        //Share preference state look
        saveTheme = SaveTheme(this)
        if(saveTheme.loadDarkModeState() == true){
            setTheme(R.style.Theme_LegoStore)
        }else{
            setTheme(R.style.ThemeLegoDark)
        }


        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        switch = findViewById<View>(R.id.swTema) as Switch?
        if(saveTheme.loadDarkModeState() == true){
            switch!!.isChecked = true
        }

        switch!!.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                saveTheme.setDarkModeState(true)
                restartApplication()
            }else{
                saveTheme.setDarkModeState(false)
                restartApplication()
            }
        }

        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()

        bundle.putString("message", "Integración con FB completa")
        analytics.logEvent("InitScreem", bundle)

        // Setup
        setup()
        session()

    }

    private fun restartApplication(){
        val i = Intent(this, AuthActivity::class.java)
        startActivity(i)
        finish()
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

        val editTextEmail: TextInputLayout  = findViewById(R.id.editTextEmail)
        val passwordEditText: TextInputLayout  = findViewById(R.id.passwordEditText)

        signUpButton.setOnClickListener {
            if(editTextEmail.editText!!.text.trim().isEmpty()){
                editTextEmail.error = "Ingrese el email"
            }else if(passwordEditText.editText!!.text.trim().isEmpty()){
                passwordEditText.error = "Ingrese su contraseña"
            }else{
                if(editTextEmail.editText!!.text.toString().isNotEmpty() && passwordEditText.editText!!.text.isNotEmpty() ){
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(editTextEmail.editText!!.text.toString(),
                        passwordEditText.editText!!.text.toString()).addOnCompleteListener {
                            if(it.isSuccessful){
                                showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                            }else{
                                Log.d("Nuevo","${ FirebaseAuth.getInstance()}")
                                showAlert(editTextEmail.editText!!.text.toString())
                            }
                    }
                }
            }
        }

        //validacion

        logInBtn.setOnClickListener {
            if(editTextEmail.editText!!.text.trim().isEmpty()){
                editTextEmail.error = "Ingrese el email"
            }else if(passwordEditText.editText!!.text.trim().isEmpty()){
                    passwordEditText.error = "Ingrese su contraseña"
            }else{
                Log.d("Authentication","Autentificando....")
                if(editTextEmail.editText!!.text.isNotEmpty() && passwordEditText.editText!!.text.isNotEmpty()){
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(editTextEmail.editText!!.text.toString(),
                        passwordEditText.editText!!.text.toString()).addOnCompleteListener {
                        if(it.isSuccessful){
                            showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                        }else{
                            showAlert(editTextEmail.editText!!.text.toString())
                            }
                        }
                }
            }
        }
    }

    private fun showAlert(string: String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario $string")
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