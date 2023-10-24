package com.example.tarea4sqlite

import kotlin.properties.Delegates

class Producto() {

    lateinit var Nombre: String
    lateinit var Descrp: String
    var Id by Delegates.notNull<Int>()

    constructor(
        id: Int,
        nombre: String,
        descrip: String
    ) : this() {
        Nombre = nombre
        Descrp = descrip
        Id = id
    }
    constructor(
        nombre: String,
        descrip: String
    ) : this() {
        Nombre = nombre
        Descrp = descrip
    }
}