package com.example.aplicacionmovil

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FacturasActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val facturas = mutableListOf<String>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facturas)

        listView = findViewById(R.id.FacturasListView)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, facturas)
        listView.adapter = adapter
        registerForContextMenu(listView)

        val crearFacturaButton = findViewById<Button>(R.id.CrearFacturaBt)
        crearFacturaButton.setOnClickListener {
            val intent = Intent(this, CrearFacturaActivity::class.java)
            startActivityForResult(intent, 2)
        }

        // Añadir el botón de regresar a la vista de clientes
        val regresarButton = findViewById<Button>(R.id.RegresarBt)
        regresarButton.setOnClickListener {
            finish()  // Cierra la actividad y regresa a la anterior en el stack
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == RESULT_OK) {
            data?.getStringExtra("factura")?.let {
                facturas.add(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.factura_context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        when (item.itemId) {
            R.id.edit -> editFactura(info.position)
            R.id.delete -> {
                facturas.removeAt(info.position)
                adapter.notifyDataSetChanged()
            }
            else -> return super.onContextItemSelected(item)
        }
        return true
    }

    private fun editFactura(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar Factura")

        val input = EditText(this)
        input.setText(facturas[position])
        builder.setView(input)

        builder.setPositiveButton("Guardar") { dialog, which ->
            facturas[position] = input.text.toString()
            adapter.notifyDataSetChanged()
        }

        builder.setNegativeButton("Cancelar") { dialog, which -> dialog.cancel() }

        builder.show()
    }
}
