package com.example.semana6

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                Toast.makeText(this, "Elemento de búsqueda seleccionado", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_settings -> {
                // Acción para el elemento de configuración
                Toast.makeText(this, "Elemento de configuración seleccionado", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun savePreferences(view: View){
        // Obtener una referencia al objeto SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("MiPref", Context.MODE_PRIVATE)

        // Editor para realizar cambios en SharedPreferences
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        // Almacenar un valor en SharedPreferences
        editor.putString("nombre", "Juan")
        editor.putInt("edad", 25)
        editor.apply() // Guardar los cambios
    }

    fun loadPreferences(view:View){
        // Obtener una referencia al objeto SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("MiPref", Context.MODE_PRIVATE)

        // Recuperar valores de SharedPreferences
        val nombre: String? = sharedPreferences.getString("nombre", "")
        val edad: Int = sharedPreferences.getInt("edad", 0)
        Toast.makeText(this, nombre, Toast.LENGTH_SHORT).show()
    }

    fun callActivity2(view: View) {
        // Crear un Intent para iniciar la Activity2
        val intent = Intent(this, MainActivity2::class.java)
        // Iniciar la Activity2 utilizando el Intent
        startActivity(intent)
    }
}