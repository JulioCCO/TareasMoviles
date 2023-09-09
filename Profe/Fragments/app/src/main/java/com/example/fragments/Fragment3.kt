package com.example.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

class Fragment3 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño del fragmento
        val view = inflater.inflate(R.layout.fragment3, container, false)

        val buttonFragment: Button = view.findViewById(R.id.F3Button3)

        buttonFragment.setOnClickListener {
            // Acción a realizar cuando se hace clic en el botón del fragmento
            // Puedes ejecutar cualquier acción que necesites aquí en el fragmento
            onButtonClicked()
        }

        return view
    }

    private fun onButtonClicked() {
        // Acción a realizar cuando se hace clic en el botón del fragmento
        // Puedes ejecutar cualquier acción que necesites aquí en el fragmento
        Toast.makeText(requireActivity(), "This is a toast", Toast.LENGTH_SHORT).show()
    }
}