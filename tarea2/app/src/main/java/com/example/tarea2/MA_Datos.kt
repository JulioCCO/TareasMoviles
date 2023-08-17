package com.example.tarea2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast


class MA_Datos : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ma_datos)
        spinnerDatosPais()

    }

    val arregloConsultado = ArrayList<String>()

    val diccionarioDatosPaises = hashMapOf<String, Any>(
        "Costa Rica" to hashMapOf<String, String>(
            "Tamaño" to "51,100 km²",
            "Poblacion" to "5.1 millones",
            "Capital" to "San José",
            "Idioma" to "Español",
            "Moneda" to "Colón (CRC)"
        ),
        "Nigeria" to hashMapOf<String, String>(
            "Tamaño" to "923,768 km²",
            "Poblacion" to "206 millones",
            "Capital" to "Abuja",
            "Idioma" to "Inglés",
            "Moneda" to "Naira (NGN)"
        ),
        "Estados Unidos" to hashMapOf<String, String>(
            "Tamaño" to "9.8 millones km²",
            "Poblacion" to "331 millones",
            "Capital" to "Washington D.C.",
            "Idioma" to "Inglés",
            "Moneda" to "Dólar estadounidense (USD)"
        ),
        "China" to hashMapOf<String, String>(
            "Tamaño" to "9.6 millones km²",
            "Poblacion" to "1.4 billones",
            "Capital" to "Pekín",
            "Idioma" to "Chino",
            "Moneda" to "Yuan (CNY)"
        ),
        "Brasil" to hashMapOf<String, String>(
            "Tamaño" to "8.5 millones km²",
            "Poblacion" to "213 millones",
            "Capital" to "Brasilia",
            "Idioma" to "Portugués",
            "Moneda" to "Real brasileño (BRL)"
        )
    )

    fun datosPais(view : View, itemSeleccionado : String) {

        val nombrePais = intent.getStringExtra("pais").toString()

        val datosPais = diccionarioDatosPaises[nombrePais] as HashMap<String, String>

        val valorSeleccionado = when (itemSeleccionado) {
            "Tamaño" -> datosPais["Tamaño"]
            "Poblacion" -> datosPais["Poblacion"]
            "Capital" -> datosPais["Capital"]
            "Idioma" -> datosPais["Idioma"]
            "Moneda" -> datosPais["Moneda"]
            else -> null
        }

        val textView = findViewById<TextView>(R.id.tV_mostrarInfo)
        textView.text = valorSeleccionado
        Toast.makeText(this@MA_Datos, "Dato agregado: $itemSeleccionado", Toast.LENGTH_SHORT).show()
        arregloConsultado.add(itemSeleccionado)
    }


    fun spinnerDatosPais(){
        val elementos = listOf("Tamaño", "Poblacion", "Capital", "Idioma", "Moneda")

        val spinner: Spinner = findViewById(R.id.spinner_datos_pais)

        // Crea un ArrayAdapter usando los elementos y el diseño predeterminado para el spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, elementos)

        // Especifica el diseño que se usará cuando se desplieguen las opciones
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Une el ArrayAdapter al Spinner
        spinner.adapter = adapter

        // Opcionalmente, puedes configurar un escuchador para detectar la selección del usuario
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val itemSeleccionado = elementos[position]
                datosPais(findViewById(R.id.spinner_datos_pais),itemSeleccionado)
                // Realiza alguna acción con el elemento seleccionado
                //Toast.makeText(this@MA_Datos, "Seleccionaste: $itemSeleccionado", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Se llama cuando no se ha seleccionado nada en el Spinner (opcional)
                Toast.makeText(this@MA_Datos, "Nada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun aceptar(view: View) {

        val intent = Intent(this, MainActivity::class.java)
        intent.putStringArrayListExtra("resultado", arregloConsultado)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun cancelar(view: View) {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}