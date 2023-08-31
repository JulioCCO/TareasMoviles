package com.example.semana6

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }
    fun loadPreferences(view: View){
        // Obtener una referencia al objeto SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("MiPref", Context.MODE_PRIVATE)

        // Recuperar valores de SharedPreferences
        val nombre: String? = sharedPreferences.getString("nombre", "")
        val edad: Int = sharedPreferences.getInt("edad", 0)
        Toast.makeText(this, nombre, Toast.LENGTH_SHORT).show()
    }
}