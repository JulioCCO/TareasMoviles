package com.example.sqlitesemana13

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "TAREA4.db"
        private const val DATABASE_VERSION = 1

        // Define la estructura de la tabla
        const val TABLE_NAME = "Producto"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "nombre"
        const val COLUMN_DESCRIPTION = "descripcion"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Crear la tabla
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME  TEXT UNIQUE, $COLUMN_DESCRIPTION TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Si necesitas realizar alguna actualización en la estructura de la base de datos, puedes hacerlo aquí
        // Por simplicidad, no se realiza ninguna acción en este ejemplo
    }


}