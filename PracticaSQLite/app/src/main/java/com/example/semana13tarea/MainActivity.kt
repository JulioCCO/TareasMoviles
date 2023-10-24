package com.example.semana13tarea

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.sqlitesemana13.Person
import com.example.sqlitesemana13.SQLiteConnector
import java.sql.SQLException

class MainActivity : AppCompatActivity() {

    lateinit var btnWrite: Button
    lateinit var btnRead: Button
    lateinit var btnDelete: Button
    lateinit var btnReadByID: Button
    lateinit var btnReadTwoOMoreParams: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        print("creando view")
        Log.d("creacion","creando usuarios")

        btnWrite = findViewById(R.id.btn_write)
        btnRead = findViewById(R.id.btn_read)
        btnDelete = findViewById(R.id.btn_delete)
        btnReadByID = findViewById(R.id.btn_read_by_id)
        btnReadTwoOMoreParams = findViewById(R.id.btn_2_or_more_params)

        SQLiteConnector.initialize(this)
/*
        btnWrite.setOnClickListener {
            writeUsers()
            print("hola")
        }

        btnRead.setOnClickListener {
            printUsers()
        }
/**/
        btnDelete.setOnClickListener {
            deleteUser()
        }

        btnReadByID.setOnClickListener {
            readPersonId()
        }

        btnReadTwoOMoreParams.setOnClickListener {
            read2params()
        }*/
    }

    fun writeUsers(view: View) {

        // Crear la base de datos y la tabla
        val db = SQLiteConnector.getWritableDatabase()
        // Puedes utilizar db para ejecutar consultas y operaciones en la base de datos

        // Agregar registros a la tabla
        val person1 = Person(1, "John Doe", 30)
        val person2 = Person(2, "Jane Smith", 25)

        // Insertar registros
        insertPerson(db, person1)
        insertPerson(db, person2)

        // Cerrar la conexión con la base de datos
        db.close()
        SQLiteConnector.closeDatabase()
    }

    private fun insertPerson(db: SQLiteDatabase, person: Person) {
        val values = ContentValues()
        values.put("name", person.name)
        values.put("age", person.age)
        db.insert("Person", null, values)
    }

    private fun readPersons(db: SQLiteDatabase): List<Person> {
        val persons = mutableListOf<Person>()
        val cursor: Cursor?
        try {
            cursor = db.rawQuery("SELECT * FROM Person", null)
        } catch (e: SQLException) {
            db.execSQL("CREATE TABLE Person (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER)")
            return emptyList()
        }

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val age = cursor.getInt(cursor.getColumnIndex("age"))
                persons.add(Person(id, name, age))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return persons
    }

    fun printUsers(view: View) {
        val context: Context = this // Obtén el contexto de tu aplicación

        // Crear la base de datos y la tabla
        val db = SQLiteConnector.getWritableDatabase()

        // Leer e imprimir los registros
        val persons = readPersons(db)
        for (person in persons) {

            Log.d("print users","ID: ${person.id}, Name: ${person.name}, Age: ${person.age}")
        }

        // Cerrar la conexión con la base de datos
        db.close()
        SQLiteConnector.closeDatabase()
    }

    fun deleteUser(){
        val db = SQLiteConnector.getWritableDatabase()
        if(deletePerson(db, 1))
            println("Persona eliminada exitosamente.")
        else
            println("No se pudo eliminar la persona.")
        db.close()
        SQLiteConnector.closeDatabase()
    }


    private fun deletePerson(db: SQLiteDatabase, personId: Int): Boolean {
        val whereClause = "id = ?"
        val whereArgs = arrayOf(personId.toString())
        val deletedRows = db.delete("Person", whereClause, whereArgs)
        return deletedRows > 0
    }

    fun readPersonId(){
        val db = SQLiteConnector.getWritableDatabase()
        // Consultar una persona por su ID
        val personToQueryId = 2
        val queriedPerson = readPersonById(db, personToQueryId)
        if (queriedPerson != null) {
            println("Persona encontrada: ID ${queriedPerson.id}, Name: ${queriedPerson.name}, Age: ${queriedPerson.age}")
        } else {
            println("No se encontró ninguna persona con el ID $personToQueryId.")
        }
        db.close()
        SQLiteConnector.closeDatabase()
    }
    private fun readPersonById(db: SQLiteDatabase, personId: Int): Person? {
        val cursor: Cursor?
        try {
            val selection = "id = ?"
            val selectionArgs = arrayOf(personId.toString())
            cursor = db.query("Person", null, selection, selectionArgs, null, null, null)
        } catch (e: SQLException) {
            return null
        }

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val name = cursor.getString(cursor.getColumnIndex("name"))
            val age = cursor.getInt(cursor.getColumnIndex("age"))
            return Person(id, name, age)
        }

        cursor.close()
        return null
    }

    fun read2params(){
        val db = SQLiteConnector.getWritableDatabase()
        // Leer e imprimir los registros
        val personsByIdAndAge = readPersons2params(db, name = "John Doe", personAge = 30)

        for (person in personsByIdAndAge) {
            println("ID: ${person.id}, Name: ${person.name}, Age: ${person.age}")
        }
        db.close()
        SQLiteConnector.closeDatabase()
    }
    private fun readPersons2params(db: SQLiteDatabase, name: String? = null, personAge: Int? = null): List<Person> {
        val persons = mutableListOf<Person>()
        var selection: String? = null
        val selectionArgs = mutableListOf<String>()

        if (name != null) {
            selection = "name = ?"
            selectionArgs.add(name)
        }

        if (personAge != null) {
            val ageSelection = "age = ?"
            if (selection != null) {
                selection += " AND $ageSelection"
            } else {
                selection = ageSelection
            }
            selectionArgs.add(personAge.toString())
        }

        val cursor: Cursor?
        try {
            cursor = db.query("Person", null, selection, selectionArgs.toTypedArray(), null, null, null)
        } catch (e: SQLException) {
            return emptyList()
        }

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val age = cursor.getInt(cursor.getColumnIndex("age"))
                persons.add(Person(id, name, age))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return persons
    }
}