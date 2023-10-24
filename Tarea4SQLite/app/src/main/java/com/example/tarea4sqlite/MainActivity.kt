package com.example.tarea4sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteCursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitesemana13.SQLiteConnector
import java.sql.SQLException


class MainActivity : AppCompatActivity() {


    private lateinit var toolbar: Toolbar
    private lateinit var editTextNombre: EditText
    private lateinit var editTextDescripcion: EditText
    private lateinit var customAdapter: CustomAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var baseDataQueries : BaseDataQueries

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SQLiteConnector.initialize(this)

        baseDataQueries = BaseDataQueries(this)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "SQLite Farmacia"


        val db = SQLiteConnector.getWritableDatabase()
        val datasetProductos = baseDataQueries.readProductos(db)

        customAdapter = CustomAdapter(datasetProductos,this)
        recyclerView = findViewById(R.id.recicler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = customAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
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

    private fun mostrarDialog() {

        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_agregar, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val dialog = builder.create()

        dialog.setOnDismissListener {

            actualizarCustomAdapter()

        }

        val btnSalir: Button = dialogView.findViewById(R.id.btn_salir)
        val btnAceptar: Button = dialogView.findViewById(R.id.btn_aceptar)

        btnAceptar.setOnClickListener {

            editTextNombre = dialogView.findViewById(R.id.etNombre)
            editTextDescripcion = dialogView.findViewById(R.id.etDescripcion)

            val ETnombre: String = editTextNombre.text.toString()
            val ETdescripcion: String = editTextDescripcion.text.toString()

            val db = SQLiteConnector.getWritableDatabase()

            if (TextUtils.isEmpty(ETnombre) || TextUtils.isEmpty(ETdescripcion)) {

                if (TextUtils.isEmpty(ETnombre)) {
                    editTextNombre.error = "Falta el nombre"
                }

                if (TextUtils.isEmpty(ETdescripcion)) {
                    editTextDescripcion.error = "Falta la descripcion"
                }
                return@setOnClickListener
            }else {

                var producto = Producto(ETnombre, ETdescripcion)
                val values = ContentValues()
                values.put("nombre", producto.Nombre)
                values.put("descripcion", producto.Descrp)
                db.insert("Producto", null, values)

                editTextNombre.text.clear()
                editTextDescripcion.text.clear()
                Toast.makeText(this, "Producto agregado", Toast.LENGTH_SHORT).show()
                baseDataQueries.printProductos()

            }
            dialog.dismiss()
            db.close()
            SQLiteConnector.closeDatabase()
        }

        btnSalir.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun actualizarCustomAdapter(){
        val db = SQLiteConnector.getWritableDatabase()
        val datasetProductos = baseDataQueries.readProductos(db)

        customAdapter = CustomAdapter(datasetProductos,this)
        customAdapter.notifyDataSetChanged()
        recyclerView.adapter = customAdapter
    }

}