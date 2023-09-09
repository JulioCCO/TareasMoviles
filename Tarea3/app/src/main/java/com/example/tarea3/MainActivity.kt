package com.example.tarea3

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        selectStorage()
        listViewEventos()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Mi Barra de Acción"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                // Acción para el elemento de búsqueda
                mostrarDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun selectStorage() {

        // Obtener una referencia al objeto SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("MiPref", Context.MODE_PRIVATE)
        val mostrarDialogo: Boolean? = sharedPreferences.getBoolean("showDialog", true)

        if (mostrarDialogo == true) {
            mostrarDialogTipoAlmacenamiento()
        }

    }

    fun mostrarDialogTipoAlmacenamiento() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_select_storage, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val dialog = builder.create()

        val sharedPreferences: SharedPreferences = getSharedPreferences("MiPref", Context.MODE_PRIVATE)

        // Editor para realizar cambios en SharedPreferences
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        val btnLocal: Button = dialogView.findViewById(R.id.btn_local)
        btnLocal.setOnClickListener {

            // Almacenar un valor en SharedPreferences
            editor.putBoolean("showDialog", false)
            editor.putBoolean("storageType", true)
            editor.apply() // Guardar los cambios
            dialog.dismiss()
        }

        val btnExterna: Button = dialogView.findViewById(R.id.btn_externa)
        btnExterna.setOnClickListener {
            // Almacenar un valor en SharedPreferences
            editor.putBoolean("showDialog", false)
            editor.putBoolean("storageType", false)
            editor.apply() // Guardar los cambios
            dialog.dismiss()
        }
        dialog.show()

    }

    // Funcion para mostrar el dialogo de los eventos
    fun mostrarDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val dialog = builder.create()

        dialog.show()
    }

    // mostrar el list view con los eventos
    fun listViewEventos() {

        val eventos = arrayOf("evento1", "evento2", "evento3", "evento4", "evento5")

        val listView: ListView = findViewById(R.id.listView_Eventos)

        // Crea un ArrayAdapter para mostrar los nombres en el ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, eventos)

        // Asocia el ArrayAdapter con el ListView
        listView.adapter = adapter

        // Configura un escuchador para el clic en los elementos del ListView
        listView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val paisSeleccionado = eventos[position]
                //callMA_Datos(findViewById(R.id.lv_paises), paisSeleccionado)
                // Muestra un Toast con el nombre seleccionado
                Toast.makeText(this@MainActivity, "Nombre seleccionado: $paisSeleccionado",
            Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun escribirArchivo(view: View) {

        val filename = "archivo_interno.txt"
        val contenido = "Este es un ejemplo de escritura en un archivo interno."

        try {
            val outputStreamWriter = OutputStreamWriter(openFileOutput(filename, Context.MODE_PRIVATE))
            outputStreamWriter.write(contenido)
            outputStreamWriter.close()
            //Toast.makeText(this@MainActivity, "Done", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}