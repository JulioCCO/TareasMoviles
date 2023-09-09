package com.example.menucontextual_fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity2 : AppCompatActivity(), fragment2.OnButtonClickListener  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val fragment = fragment2()
        fragment.setOnButtonClickListener(this)

        //val bt1: Button = findViewById(R.id.F2Button3)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout_fragment, fragment)
            .commit()
    }

    override fun onButtonClicked() {
        Toast.makeText(this, "Sirve", Toast.LENGTH_SHORT).show()
    }

    override fun onButtonClicked2() {
        Toast.makeText(this, "Sirve el 2", Toast.LENGTH_SHORT).show()
    }


}