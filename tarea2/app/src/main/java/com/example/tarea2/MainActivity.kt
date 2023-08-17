package com.example.tarea2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {


    private lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listViewPaises()

        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val resultado = data?.getStringArrayListExtra("resultado")
                // Hacer algo con el resultado recibido de Activity3
                Toast.makeText(this@MainActivity, "Resultado ok", Toast.LENGTH_SHORT).show()

                if (resultado != null) {
                    for(elem in resultado) {
                        Toast.makeText(this@MainActivity, "EL usuario reviso: $elem", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else {
                Toast.makeText(this@MainActivity, "Resultado fallido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun listViewPaises() {

        val paises = arrayOf("Costa Rica", "Nigeria", "Estados Unidos", "China", "Brasil")

        val listView: ListView = findViewById(R.id.lv_paises)

        // Crea un ArrayAdapter para mostrar los nombres en el ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, paises)

        // Asocia el ArrayAdapter con el ListView
        listView.adapter = adapter

        // Configura un escuchador para el clic en los elementos del ListView
        listView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val paisSeleccionado = paises[position]
                callMA_Datos(findViewById(R.id.lv_paises), paisSeleccionado)
                // Muestra un Toast con el nombre seleccionado
               // Toast.makeText(this@MainActivity, "Nombre seleccionado: $paisSeleccionado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun callMA_Datos(view: View, pais: String ) {

        // Crear un Intent para iniciar la Activity MA_Datos
        val intent = Intent(this, MA_Datos::class.java)

        // Opcional: Puedes enviar datos extras a la Activity2 utilizando putExtra
        intent.putExtra("pais", pais)

        // Iniciar la Activity2 utilizando el Intent
        startForResult.launch(intent)
    }

}