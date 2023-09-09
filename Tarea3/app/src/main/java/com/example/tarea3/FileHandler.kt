package com.example.tarea3

import android.content.Context
import android.view.View
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
class FileHandler(private val context: Context) {
    fun escribirArchivoInterno(filename: String, data: String) {

        try {
            val outputStreamWriter = context.openFileOutput(filename, Context.MODE_PRIVATE)
            outputStreamWriter.write(data.toByteArray())
            outputStreamWriter.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun leerArchivoInterno(filename: String): String {

        val file = File(context.filesDir, filename)
        val stringBuilder = StringBuilder()
        try {

            val bufferedReader = BufferedReader(FileReader(file))
            var linea: String? = bufferedReader.readLine()
            while (linea != null) {
                stringBuilder.append(linea).append("\n")
                linea = bufferedReader.readLine()
            }
            bufferedReader.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }

}