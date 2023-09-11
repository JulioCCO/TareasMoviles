package com.example.tarea3

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {

    private var eventos = mutableListOf<Evento>()
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>

    private val gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView_Eventos)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, eventos.map { it.nombre })
        listView.adapter =adapter

        selectStorage()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Mi Barra de Acción"

        listViewEventos()
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

    private fun selectStorage() {

        // Obtener una referencia al objeto SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("MiPref", Context.MODE_PRIVATE)
        val mostrarDialogo: Boolean = sharedPreferences.getBoolean("showDialog", true)

        if (mostrarDialogo) {
            mostrarDialogTipoAlmacenamiento()
        }

    }

    private fun mostrarDialogTipoAlmacenamiento() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_select_storage, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val dialog = builder.create()

        // Editor para realizar cambios en SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("MiPref", Context.MODE_PRIVATE)
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
    private fun mostrarDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val dialog = builder.create()

        val btnAceptar: Button = dialogView.findViewById(R.id.btn_aceptar)
        btnAceptar.setOnClickListener {

            // Almacenar un valor en SharedPreferences

            val nombre: EditText = dialogView.findViewById(R.id.editTextNombre)
            val descripcion: EditText = dialogView.findViewById(R.id.editTextDescripcion)
            val simpleCalendarView = findViewById<View>(R.id.calendarView) as CalendarView
            val fecha: String = simpleCalendarView.date.toString()

            eventos.add(Evento(fecha,nombre.toString(),descripcion.toString()))
            val sharedPreferences: SharedPreferences = getSharedPreferences("MiPref", Context.MODE_PRIVATE)
            val storageType: Boolean = sharedPreferences.getBoolean("storageType", true)
            if(storageType){
                //agarrar la local y escribir Eventos\
                escribirArchivoInterno("archivo_interno.txt")

            }else{
                // agarrar la externa y escribir Eventos

            }
            //adapter.notifyDataSetChanged()

            dialog.dismiss()
        }

        val btnCerrar: Button = dialogView.findViewById(R.id.btn_cerrar)
        btnCerrar.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()


    }

    // mostrar el list view con los eventos
    private fun listViewEventos() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MiPref", Context.MODE_PRIVATE)
        val storageType: Boolean = sharedPreferences.getBoolean("storageType", true)

        if(storageType){
            //agarrar la local y cargar Eventos
            leerArchivoInterno("archivo_interno.txt")

        }else{
            // agarrar la externa y cargar Eventos

        }
        //val paises = arrayOf("Costa Rica", "Nigeria", "Estados Unidos", "China", "Brasil")

        val listView: ListView = findViewById(R.id.listView_Eventos)

        // Crea un ArrayAdapter para mostrar los nombres en el ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, eventos.map { it.nombre })

        // Asocia el ArrayAdapter con el ListView
        listView.adapter = adapter

        // Configura un escuchador para el clic en los elementos del ListView
        listView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val eventoSeleccionado = eventos[position].nombre

                // Muestra un Toast con el nombre seleccionado
                Toast.makeText(this@MainActivity, "Nombre seleccionado: $eventoSeleccionado",
            Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun escribirArchivoInterno(filename: String) {
        try {
            val outputStreamWriter = OutputStreamWriter(openFileOutput(filename, Context.MODE_PRIVATE))
            for (event in eventos) {
                val json = gson.toJson(event)
                outputStreamWriter.write(json+ "\n")
            }
            outputStreamWriter.close()
            Toast.makeText(this@MainActivity, "Escritura correcta", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun leerArchivoInterno(filename: String){

        try {
            val fileInputStream = openFileInput(filename)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var linea: String? = bufferedReader.readLine()

            while (linea != null) {
                val evento = gson.fromJson(linea, Evento::class.java)
                eventos.add(evento)
                linea = bufferedReader.readLine()
            }

            bufferedReader.close()
            Toast.makeText(this@MainActivity, "Lectura correcta $eventos", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}