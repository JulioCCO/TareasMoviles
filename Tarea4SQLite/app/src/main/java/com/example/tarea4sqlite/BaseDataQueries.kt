package com.example.tarea4sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.sqlitesemana13.DatabaseHelper
import com.example.sqlitesemana13.SQLiteConnector
import java.sql.SQLException


class BaseDataQueries() {

    lateinit var context: Context

    constructor(
        contextView: Context,

        ) : this() {
        context = contextView

    }

    fun printProductos() {


        // Crear la base de datos y la tabla
        val db = SQLiteConnector.getWritableDatabase()

        // Leer e imprimir los registros
        val ListaProductos = readProductos(db)
        Log.d("productos", "---------------- INICIO LISTA DE PRODUCTOS ----------------")
        for (prod in ListaProductos) {
            Log.d("productos", "ID: ${prod.Id}, Name: ${prod.Nombre}, Age: ${prod.Nombre}")
        }
        Log.d("productos", "---------------- FIN LISTA DE PRODUCTOS ----------------")
        // Cerrar la conexión con la base de datos
        db.close()
        SQLiteConnector.closeDatabase()
    }

    fun readProductos(db: SQLiteDatabase): List<Producto> {

        val productos = mutableListOf<Producto>()
        val cursor: Cursor?
        try {
            cursor = db.rawQuery("SELECT * FROM Producto", null)
        } catch (e: SQLException) {
            db.execSQL("CREATE TABLE Producto (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT)")
            return emptyList()
        }

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                val descripcion = cursor.getString(cursor.getColumnIndex("descripcion"))
                productos.add(Producto(id, nombre, descripcion))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return productos
    }

    private fun readProductById(db: SQLiteDatabase, productId: Int): Producto? {
        val cursor: Cursor?
        try {
            val selection = "id = ?"
            val selectionArgs = arrayOf(productId.toString())
            cursor = db.query("Producto", null, selection, selectionArgs, null, null, null)
        } catch (e: SQLException) {
            return null
        }

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val name = cursor.getString(cursor.getColumnIndex("name"))
            val descrip = cursor.getString(cursor.getColumnIndex("age"))
            return Producto(id, name, descrip)
        }

        cursor.close()
        return null
    }

    fun deleteProduct(Id: Int) {
        val db = SQLiteConnector.getWritableDatabase()
        if (deleteProductoByID(db, Id))
            println("Persona eliminada exitosamente.")
        else
            println("No se pudo eliminar la persona.")
        db.close()
        SQLiteConnector.closeDatabase()
    }

    private fun deleteProductoByID(db: SQLiteDatabase, Id: Int): Boolean {
        val whereClause = "id = ?"
        val whereArgs = arrayOf(Id.toString())
        val deletedRows = db.delete("Producto", whereClause, whereArgs)
        return deletedRows > 0
    }

    fun guardarCambios(productoEditado: Producto): Int {
        val db = SQLiteConnector.getWritableDatabase()

        val valoresParaActualizar = ContentValues()
        valoresParaActualizar.put("nombre", productoEditado.Nombre)
        valoresParaActualizar.put("descripcion",productoEditado.Descrp)
        // where id...
        val campoParaActualizar = "id = ?"
        // ... = idProducto
        val argumentosParaActualizar =
            arrayOf<String>(java.lang.String.valueOf(productoEditado.Id))
        return db.update(
            "Producto",
            valoresParaActualizar,
            campoParaActualizar,
            argumentosParaActualizar
        )
    }
}