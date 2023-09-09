package com.example.menucontextual_fragment

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    private lateinit var et1: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        et1 = findViewById(R.id.eT_name)
        registerForContextMenu(et1) // Registra el textView para el menú contextual

        // Registrar menús contextuales para las vistas específicas
        /*val view1 = findViewById<View>(R.id.view1)
        registerForContextMenu(view1)

        val view2 = findViewById<View>(R.id.view2)
        registerForContextMenu(view2)*/

        // Agregar el fragmento al contenedor
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout_fragment, fragment1())
            .commit()
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_contextual, menu) // Infla el menú contextual
        /*
         if (v?.id == R.id.view1) {
        menuInflater.inflate(R.menu.menu_context_view1, menu)
    } else if (v?.id == R.id.view2) {
        menuInflater.inflate(R.menu.menu_context_view2, menu)
    }
        * */
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.it1 -> {
                // Acción para editar
                et1.setBackgroundColor(Color.rgb(255,0,0))
                Toast.makeText(this, "et1", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.it2 -> {
                // Acción para eliminar
                et1.setBackgroundColor(Color.rgb(0,255,0))
                Toast.makeText(this, "et2", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.it3 -> {
                // Acción para eliminar
                et1.setBackgroundColor(Color.rgb(0,0,255))
                Toast.makeText(this, "et3", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.it4 -> {
                // Acción para eliminar
                et1.setBackgroundColor(Color.BLACK)
                Toast.makeText(this, "et4", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    fun mostrarMenuEmergente(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.pop_menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.pop1 -> {
                    // Acción para la opción 1
                    et1.setBackgroundColor(Color.rgb(0,255,0))
                    true
                }
                R.id.pop2 -> {
                    // Acción para la opción 2
                    et1.setBackgroundColor(Color.rgb(0,0,255))
                    true
                }
                R.id.pop3 -> {
                    // Acción para la opción 3
                    et1.setBackgroundColor(Color.BLACK)
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }
    fun activity2(view: View){
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
    }
    fun blankFragment(view: View){
        val param1 = "Ejemplo de parámetro"
        val param2 = "sdfsd"

        val fragment = fragment3.newInstance(param1, param2)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Reemplazar R.id.fragment_container con el ID de tu contenedor de fragmentos
        fragmentTransaction.replace(R.id.frame_layout_fragment, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }
}