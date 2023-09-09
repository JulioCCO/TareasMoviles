package com.example.vistas2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
    }

    fun enviar(view: View){
        val resultado = "Holis desde Act 3"

        val intent = Intent()
        intent.putExtra("resultado", resultado)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}