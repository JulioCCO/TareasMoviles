package com.example.tarea3

import android.content.Context
import android.content.Intent
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
import android.widget.CalendarView.OnDateChangeListener
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private var eventos = mutableListOf<Evento>()
    private var eventosNombres = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>

    private val gson = Gson()

    private lateinit var startForResult: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        selectStorage()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "ACTION BAR"

        listViewEventos()

        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == RESULT_OK){
                val data: Intent? = result.data
                val resultado = data?.getStringExtra("resultado")
                Toast.makeText(this@MainActivity, "Resultado obtenido: $resultado", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@MainActivity, "Resultado fallido:", Toast.LENGTH_SHORT).show()
            }
        }
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
        var fecha: String = ""
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val dialog = builder.create()

        val btnAceptar: Button = dialogView.findViewById(R.id.btn_aceptar)

        // Busca el CalendarView dentro del layout inflado
        val calendarView: CalendarView = dialogView.findViewById(R.id.calendarView)

        calendarView.setOnDateChangeListener { _, anio, mes, dia ->

            fecha = "$dia/${mes +1}/$anio"
        }

        btnAceptar.setOnClickListener {

            // Almacenar un valor en SharedPreferences

            val ETnombre: EditText = dialogView.findViewById(R.id.editTextNombre)
            val ETdescripcion: EditText = dialogView.findViewById(R.id.editTextDescripcion)

            eventos.add(Evento(fecha,ETnombre.text.toString(),ETdescripcion.text.toString()))
            eventosNombres.add(ETnombre.text.toString())
            adapter.notifyDataSetChanged()
            val sharedPreferences: SharedPreferences = getSharedPreferences("MiPref", Context.MODE_PRIVATE)
            val storageType: Boolean = sharedPreferences.getBoolean("storageType", true)
            if(storageType){
                //agarrar la local y escribir Eventos\
                escribirArchivoInterno("archivo_interno.txt")

            }else{
                // agarrar la externa y escribir Eventos
                escribirExterno("archivo_externo.txt")
            }

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
            leerExterno("archivo_interno.txt")

        }

        val listView: ListView = findViewById(R.id.listView_Eventos)

        // Crea un ArrayAdapter para mostrar los nombres en el ListView
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,eventosNombres)

        // Asocia el ArrayAdapter con el ListView
        listView.adapter = adapter

        // Configura un escuchador para el clic en los elementos del ListView
        listView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val eventoSeleccionado = eventosNombres[position]
                callActivity2(eventoSeleccionado)
                // Muestra un Toast con el nombre seleccionado
                Toast.makeText(this@MainActivity, "Nombre seleccionado: $eventoSeleccionado",
            Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun callActivity2(nombreEvento: String){
        val intent = Intent(this, MainActivity2::class.java)

        for (evento in eventos){
            if(evento.nombre==nombreEvento) {

                intent.putExtra("nombre", evento.nombre)
                intent.putExtra("descripcion", evento.descripcion)
                intent.putExtra("fecha", evento.fecha)
            }else{
                intent.putExtra("error", true)
            }
        }
        startForResult.launch(intent)
    }

    fun escribirArchivoInterno(filename: String) {
        try {
            val outputStreamWriter = OutputStreamWriter(openFileOutput(filename, Context.MODE_PRIVATE))
            val eventosJson = gson.toJson(eventos)
            outputStreamWriter.write(eventosJson)
            outputStreamWriter.close()
            Toast.makeText(this@MainActivity, "Escritura interna correcta", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun escribirExterno(filename: String) {

        try {
            val outputStream = FileOutputStream(filename)
            val eventosJson = gson.toJson(eventos)
            outputStream.write(eventosJson.toByteArray())
            outputStream.close()
            Toast.makeText(this@MainActivity, "Escritura externa correcta", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun leerArchivoInterno(filename: String){

        try {
            val fileInputStream = openFileInput(filename)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val contenido = StringBuilder()
            var linea: String? = bufferedReader.readLine()
            while (linea != null) {
                contenido.append(linea).append("\n")
                linea = bufferedReader.readLine()
            }
            bufferedReader.close()
            val eventosJson = contenido.toString()

            // Deserialize the JSON back to a list of Evento objects
            val eventosList: List<Evento> = gson.fromJson(eventosJson, object : TypeToken<List<Evento>>() {}.type)

            // Update your existing eventos list with the deserialized data
            eventos.clear() // Clear the existing list if needed
            eventos.addAll(eventosList)
            eventosNombres.clear()
            eventosNombres.addAll(eventosList.map { it.nombre })
            Toast.makeText(this@MainActivity, "Lectura correcta", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun leerExterno(filename: String){

        try {
            val bufferedReader = BufferedReader(FileReader(filename))
            val stringBuilder = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            bufferedReader.close()
            val content = stringBuilder.toString()
            val eventosJson = content

            // Deserialize the JSON back to a list of Evento objects
            val eventosList: List<Evento> = gson.fromJson(eventosJson, object : TypeToken<List<Evento>>() {}.type)

            // Update your existing eventos list with the deserialized data
            eventos.clear() // Clear the existing list if needed
            eventos.addAll(eventosList)
            eventosNombres.clear()
            eventosNombres.addAll(eventosList.map { it.nombre })
            Toast.makeText(this@MainActivity, "Lectura externa correcta", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}