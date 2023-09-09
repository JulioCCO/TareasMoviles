package com.example.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity2 : AppCompatActivity(), Fragment2.OnButtonClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val fragment = Fragment2()
        fragment.setOnButtonClickListener(this)

        //val bt1: Button = findViewById(R.id.F2Button3)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onButtonClicked() {
        Toast.makeText(this, "Sirve", Toast.LENGTH_SHORT).show()
    }

    override fun onButtonClicked2() {
        Toast.makeText(this, "Sirve el 2", Toast.LENGTH_SHORT).show()
    }
}