package com.example.recyclerview

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val textView = findViewById<TextView>(R.id.textViewSecond)
        val textParameter = intent.getStringExtra("textParameter")

        if (textParameter != null) {
            textView.text = textParameter
        }
    }
}
