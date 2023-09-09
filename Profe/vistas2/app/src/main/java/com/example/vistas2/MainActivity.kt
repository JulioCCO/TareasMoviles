package com.example.vistas2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spinnerExample()
        listViewExample()

        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val resultado = data?.getStringExtra("resultado")
                // Hacer algo con el resultado recibido de Activity3
                Toast.makeText(this@MainActivity, "Resultado: $resultado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun suma(view: View){
        val et1: EditText = findViewById(R.id.et1)
        val et2: EditText = findViewById(R.id.et2)
        val resu: TextView = findViewById(R.id.tv3)
        val r1: RadioButton = findViewById(R.id.r1)
        val r2: RadioButton = findViewById(R.id.r2)
        val cb: CheckBox = findViewById(R.id.checkBox)

        var valor1 = et1.text.toString().toInt()
        var valor2 = et2.text.toString().toInt()

        if (cb.isChecked) {
            var resultado = if (r1.isChecked)
                valor1 + valor2
            else
                valor1 - valor2

            resu.text = resultado.toString()
        }else
            resu.text = "no check"
    }

    fun spinnerExample(){
        val elementos = listOf("Elemento 1", "Elemento 2", "Elemento 3")

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
                // Realiza alguna acción con el elemento seleccionado
                Toast.makeText(this@MainActivity, "Seleccionaste: $itemSeleccionado", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Se llama cuando no se ha seleccionado nada en el Spinner (opcional)
                Toast.makeText(this@MainActivity, "Nada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun listViewExample(){
        val nombres = arrayOf("Juan", "María", "Carlos", "Luisa", "Ana")

        val listView: ListView = findViewById(R.id.lv1)

        // Crea un ArrayAdapter para mostrar los nombres en el ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombres)

        // Asocia el ArrayAdapter con el ListView
        listView.adapter = adapter

        // Configura un escuchador para el clic en los elementos del ListView
        listView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val nombreSeleccionado = nombres[position]
                // Muestra un Toast con el nombre seleccionado
                Toast.makeText(this@MainActivity, "Nombre seleccionado: $nombreSeleccionado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun callActivity2(view: View){
        // Crear un Intent para iniciar la Activity2
        val intent = Intent(this, MainActivity2::class.java)

        // Opcional: Puedes enviar datos extras a la Activity2 utilizando putExtra
        intent.putExtra("nombre", "John")
        intent.putExtra("edad", 30)

        // Iniciar la Activity2 utilizando el Intent
        startActivity(intent)
    }

    fun callActivity3Result(view: View){


        val intent = Intent(this, MainActivity3::class.java)
        startForResult.launch(intent)
    }

    fun mostrarDialog(view: View) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val dialog = builder.create()

        val btnCerrar:Button = dialogView.findViewById(R.id.btnCerrar)
        btnCerrar.setOnClickListener {
            dialog.dismiss() // Cierra el diálogo cuando se hace clic en el botón "Cerrar"
        }

        dialog.show()
    }

    private fun mostrarDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Título del Diálogo")
        builder.setMessage("¡Esto es un diálogo de ejemplo!")
        builder.setPositiveButton("Aceptar") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun mostrarDialog2() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmación")
        builder.setMessage("¿Estás seguro de realizar esta acción?")

        builder.setPositiveButton("Sí") { _, _ ->
            // Acción a realizar si el usuario selecciona "Sí"
            //realizarAccion()
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}