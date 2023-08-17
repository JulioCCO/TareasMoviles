package com.example.vistas2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // Obtener los datos extras del Intent
        val nombre = intent.getStringExtra("nombre")
        val edad = intent.getIntExtra("edad", 0) // Se proporciona un valor predeterminado en caso de que no se encuentre el dato
    }
}