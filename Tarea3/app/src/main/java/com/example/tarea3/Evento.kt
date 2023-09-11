package com.example.tarea3

import com.google.gson.annotations.SerializedName

class Evento(fecha: String, nombre: String, descripcion: String) {

    @SerializedName("fecha")
    var fecha: String = ""

    @SerializedName("nombre")
    var nombre: String = ""

    @SerializedName("descripcion")
    var descripcion: String = ""

    init {
        this.fecha = fecha
        this.nombre = nombre
        this.descripcion = descripcion
    }

}