package com.example.recycleviewexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.MyRecycle)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val titulo = listOf("Mango", "Naranaja")
        val precio = listOf("10", "20")
        val imagen = listOf(R.drawable.mango, R.drawable.naranja)
        adapter = MyAdapter(titulo, precio, imagen)
        recyclerView.adapter = adapter
    }
}