package com.example.recycleviewexample

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val data: List<String>, data1: List<String>, data2: List<Int>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
       return data.size
    }
}