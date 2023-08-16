package com.example.tarea1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spinnerExample()

    }
    var operacion: String = ""

    fun operar(view: View){

        val et_valor1: EditText = findViewById(R.id.eT_valor1)
        val et_valor2: EditText = findViewById(R.id.eT_valor2)
        val tV_Resultado: TextView = findViewById(R.id.tV_Resultado)
        var resultado: Int = 0

        val verificarValor1 = et_valor1.text.toString()
        val verificarValor2 = et_valor2.text.toString()

        // Verificar que los campos no esten vacios
        if(verificarValor1.isNotEmpty() && verificarValor2.isNotEmpty()) {

            val verificarTipoValor1 = et_valor1.text.toString().toIntOrNull()
            val verificarTipoValor2 = et_valor2.text.toString().toIntOrNull()

            if(verificarTipoValor1 == null || verificarTipoValor2 == null ) {

                if(verificarTipoValor1 == null) {
                    et_valor1.error = "Ingrese un numero."
                }
                if(verificarTipoValor2 == null) {
                    et_valor2.error = "Ingrese un numero."
                }
            }
            else {

                val valor1 = et_valor1.text.toString().toInt()
                val valor2 = et_valor2.text.toString().toInt()
                when(operacion) {
                    "Suma" -> resultado = valor1 + valor2
                    "Resta" -> resultado = valor1 - valor2
                    "Multiplicacion" -> resultado = valor1 * valor2
                    "Division" -> resultado = valor1 / valor2
                }

                tV_Resultado.text = (resultado).toString()
                Toast.makeText(this@MainActivity, "Resultado generado.", Toast.LENGTH_SHORT).show()
            }



        }
        else if(verificarValor1.isEmpty() || verificarValor2.isEmpty()) {

            if(verificarValor1.isEmpty()) {
                et_valor1.error = "Campo requerido"
            }
            if(verificarValor2.isEmpty()) {
                et_valor2.error = "Campo requerido"
            }

            Toast.makeText(this@MainActivity, "Por favor llene todos los espacios.", Toast.LENGTH_SHORT).show()
        }


    }

    fun spinnerExample(){

        val elementos = resources.getStringArray(R.array.operaciones)

        val spinner: Spinner = findViewById(R.id.spinner)

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
                operacion = itemSeleccionado

                // Realiza alguna acción con el elemento seleccionado
                Toast.makeText(this@MainActivity, "Seleccionaste: $itemSeleccionado", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Se llama cuando no se ha seleccionado nada en el Spinner (opcional)
               Toast.makeText(this@MainActivity, "Nada", Toast.LENGTH_SHORT).show()
            }
        }
    }
}