package com.example.aplicacionmovil

import android.content.Intent
import android.os.Bundle
import android.text.InputType
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

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val clientes = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listViewClientes)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, clientes)
        listView.adapter = adapter

        val crearClienteButton = findViewById<Button>(R.id.crearCliente)
        crearClienteButton.setOnClickListener {
            val intent = Intent(this, CrearClienteActivity::class.java)
            startActivityForResult(intent, 1)  // Use request code to identify the result
        }

        registerForContextMenu(listView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            data?.getStringExtra("cliente")?.let {
                clientes.add(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        when (item.itemId) {
            R.id.edit -> editCliente(info.position)
            R.id.delete -> deleteCliente(info.position)
            R.id.view_facturas -> viewFacturas(info.position)
            else -> return super.onContextItemSelected(item)
        }
        return true
    }

    private fun editCliente(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar Cliente")
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.setText(clientes[position])
        builder.setView(input)

        builder.setPositiveButton("Guardar") { dialog, which ->
            clientes[position] = input.text.toString()
            adapter.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancelar", { dialog, which -> dialog.cancel() })

        builder.show()
    }

    private fun deleteCliente(position: Int) {
        clientes.removeAt(position)
        adapter.notifyDataSetChanged()
    }

    private fun viewFacturas(position: Int) {
        val intent = Intent(this, FacturasActivity::class.java)
        intent.putExtra("clienteNombre", clientes[position])
        startActivity(intent)
    }
}
