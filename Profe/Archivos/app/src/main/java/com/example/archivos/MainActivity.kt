package com.example.archivos

import DTO.Evento
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import org.json.JSONObject
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
       // savePeopleData()
    }

    fun escribirArchivo(view: View) {

        val filename = "archivo_interno.txt"
        val contenido = "Este es un ejemplo de escritura en un archivo interno."

        try {
            val outputStreamWriter = OutputStreamWriter(openFileOutput(filename, Context.MODE_PRIVATE))
            outputStreamWriter.write(contenido)
            outputStreamWriter.close()
            Toast.makeText(this@MainActivity, "Done", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun leerArchivo(view:View){
        val filename = "archivo_interno.txt"

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

            // Ahora puedes usar el contenido leído
            Toast.makeText(this@MainActivity, "Contenido del archivo interno: $contenido", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun escribirExterno(view: View) {
        val textToWrite = "Hello, External Storage!"

        val file = File(getExternalFilesDir(null), "example.txt")

        try {
            val outputStream = FileOutputStream(file)
            outputStream.write(textToWrite.toByteArray())
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun leerExterno(view:View){
        val file = File(getExternalFilesDir(null), "example.txt")

        try {
            val bufferedReader = BufferedReader(FileReader(file))
            val stringBuilder = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            bufferedReader.close()
            val content = stringBuilder.toString()
            println("Read content: $content")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}