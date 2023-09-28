package com.example.semana10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = FirebaseFirestore.getInstance()
    }

    fun writeData(view: View){
        val usuario = hashMapOf(
            "Nombre" to "John Doe",
            "Edad" to 10
        )

        db.collection("usuario")
            .add(usuario)
            .addOnSuccessListener { documentReference ->
                println("DocumentSnapshot agregado con ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                println("Error al agregar documento: $e")
            }
    }

    fun readData(view: View){
        db.collection("usuario")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    println("${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                println("Error al obtener documentos: $exception")
            }
    }

    fun updateUser(view: View){
        // ID del usuario que quieres actualizar
        val userId = "3CjpBTbvHmMuiqyvobIP"
        // Crear un mapa con la nueva información que quieres actualizar
        val nuevosDatos = hashMapOf(
            "Edad" to 30 // Nueva edad
        )

        // Actualizar el documento en Firestore
        db.collection("usuario")
            .document(userId)
            .update(nuevosDatos as Map<String, Any>)
            .addOnSuccessListener {
                println("Datos actualizados exitosamente para el usuario con ID: $userId")
            }
            .addOnFailureListener { e ->
                println("Error al actualizar datos: $e")
            }
    }

    fun getUserID(view: View){
        // Nombre del usuario que deseas buscar
        val nombreUsuario = "John Doe"

        // Realizar la consulta para obtener el ID del usuario por su nombre
        db.collection("usuario")
            .whereEqualTo("Nombre", nombreUsuario)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    // Aquí obtienes el ID del documento que coincide con el nombre
                    val userId = document.id
                    println("El ID del usuario $nombreUsuario es: $userId")
                }
            }
            .addOnFailureListener { exception ->
                println("Error al obtener ID del usuario: $exception")
            }
    }

    fun deleteUser(view: View){
        // ID del usuario que deseas eliminar
        val userId = "3CjpBTbvHmMuiqyvobIP"

        // Obtén una referencia al documento del usuario
        val docRef = db.collection("usuario").document(userId)

        // Eliminar el documento
        docRef.delete()
            .addOnSuccessListener {
                println("Usuario con ID $userId eliminado exitosamente.")
            }
            .addOnFailureListener { e ->
                println("Error al eliminar usuario: $e")
            }
    }
}