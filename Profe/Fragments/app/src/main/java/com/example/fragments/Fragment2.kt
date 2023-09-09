package com.example.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class Fragment2 : Fragment() {

    private var buttonClickListener: OnButtonClickListener? = null
    private lateinit var buttonFragment: Button
    private lateinit var button4: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño del fragmento
        val view = inflater.inflate(R.layout.fragment2, container, false)

        buttonFragment = view.findViewById(R.id.F3Button3)

        buttonFragment.setOnClickListener {
            buttonClickListener?.onButtonClicked()
        }
        button4 = view.findViewById(R.id.F2Button4)

        button4.setOnClickListener {
            buttonClickListener?.onButtonClicked2()
        }

        return view
    }
    //Esta función se llama desde la activity donde se seteó el fragment
    fun setOnButtonClickListener(listener: OnButtonClickListener) {
        buttonClickListener = listener
    }

    interface OnButtonClickListener {
        fun onButtonClicked()
        fun onButtonClicked2()
    }

}