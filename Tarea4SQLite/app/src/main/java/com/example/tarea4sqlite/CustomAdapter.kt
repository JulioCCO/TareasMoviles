package com.example.tarea4sqlite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitesemana13.SQLiteConnector


class CustomAdapter(private var dataSet: List<Producto>, private val context: Context) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    lateinit var baseDataQueries: BaseDataQueries

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tV_nombre: TextView
        val tV_descrip: TextView
        val btnEditar: ImageButton
        val btnEliminar: ImageButton

        init {
            // Define click listener for the ViewHolder's View
            tV_nombre = view.findViewById(R.id.tVNombreTR)
            tV_descrip = view.findViewById(R.id.tVDescripcionTR)
            btnEditar = view.findViewById(R.id.btn_editarTR)
            btnEliminar = view.findViewById(R.id.btn_eliminarTR)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.text_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.tV_nombre.text = dataSet[position].Nombre
        viewHolder.tV_descrip.text = dataSet[position].Descrp
        baseDataQueries = BaseDataQueries()

        viewHolder.btnEditar.setOnClickListener {

            Toast.makeText(
                viewHolder.itemView.context,
                "${dataSet[position].Id}",
                Toast.LENGTH_SHORT,
            ).show()

            mostrarDialog(dataSet[position].Id, dataSet[position].Nombre, dataSet[position].Descrp)


        }

        viewHolder.btnEliminar.setOnClickListener {

            baseDataQueries.deleteProduct(dataSet[position].Id)
            Toast.makeText(
                viewHolder.itemView.context,
                "Producto eliminado.",
                Toast.LENGTH_SHORT,
            ).show()
            val db = SQLiteConnector.getWritableDatabase()
            val datasetProductos = baseDataQueries.readProductos(db)
            dataSet = datasetProductos
            notifyDataSetChanged()
            db.close()
            SQLiteConnector.closeDatabase()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size


    fun mostrarDialog(id: Int, nombreData: String, descrpData: String) {

        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_editar, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(dialogView)
        val dialog = builder.create()

        val nombre = dialogView.findViewById<TextView>(R.id.etNombreEditar)
        nombre.text = nombreData

        val descripcion = dialogView.findViewById<TextView>(R.id.etDescripcionEditar)
        descripcion.text = descrpData

        val btnAceptar: Button = dialogView.findViewById(R.id.btn_aceptarEditar)
        val btnSalir: Button = dialogView.findViewById(R.id.btn_salirEditar)

        // se edita el producto seleccionado
        btnAceptar.setOnClickListener {
            var producto = Producto(id, nombre.text.toString(), descripcion.text.toString())
            baseDataQueries.guardarCambios(producto)


            val index = dataSet.indexOfFirst { it.Id == id }
            if (index != -1) {
                dataSet[index].Nombre = producto.Nombre
                dataSet[index].Descrp = producto.Descrp
                notifyDataSetChanged()
            }
            Toast.makeText(context, "producto actualizado", Toast.LENGTH_SHORT).show()

            dialog.dismiss()
        }

        btnSalir.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
