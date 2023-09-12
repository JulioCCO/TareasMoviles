package com.example.tarea3

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        cargarDatos()
    }

    fun cargarDatos() {

        val nombre = intent.getStringExtra("nombre").toString()
        val descripcion = intent.getStringExtra("descripcion").toString()
        val fecha = intent.getStringExtra("fecha").toString()

        val textViewNombre = findViewById<TextView>(R.id.tv_MostrarNombre)
        textViewNombre.text = nombre

        val textViewDescrip = findViewById<TextView>(R.id.tv_mostrarDescrip)
        textViewDescrip.text = descripcion

        val textViewFecha = findViewById<TextView>(R.id.tv_mostrarFecha)
        textViewFecha.text = fecha

    }

    fun aceptar(view: View) {

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("resultado", "correcto")
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun cerrar(view: View) {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}