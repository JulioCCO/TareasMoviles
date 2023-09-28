package com.example.recyclerview

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val data: List<String>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        val button: Button = itemView.findViewById(R.id.button)
        val button2: Button = itemView.findViewById(R.id.button2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val text = data[position]
        holder.textView.text = text

        // Configura un OnClickListener para el botón
        holder.button.setOnClickListener {
            Toast.makeText(context, data[position], Toast.LENGTH_SHORT).show()
        }

        // Configura un OnClickListener para el segundo botón (button2)
        holder.button2.setOnClickListener {
            // Abre la segunda actividad y envía el texto como parámetro
            val intent = Intent(context, SecondActivity::class.java)
            intent.putExtra("textParameter", text)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

