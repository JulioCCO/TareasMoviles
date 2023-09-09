package com.example.fragments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Agregar el fragmento al contenedor
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, Fragment1())
            .commit()
    }

    fun activity2(view: View){
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
    }

    fun blankFragment(view: View){
        val param1 = "Ejemplo de parámetro"
        val param2 = "sdfsd"

        val fragment = BlankFragment.newInstance(param1, param2)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

// Reemplazar R.id.fragment_container con el ID de tu contenedor de fragmentos
        fragmentTransaction.replace(R.id.fragment_container2, fragment)
        //fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}