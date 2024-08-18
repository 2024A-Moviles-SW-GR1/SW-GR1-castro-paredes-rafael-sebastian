package com.example.aplicacionexamen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class FacturasActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val gestorSQL: GestorSQL = GestorSQL(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facturas)

        listView = findViewById(R.id.FacturasListView)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, loadFacturas())
        listView.adapter = adapter
        registerForContextMenu(listView)

        val crearFacturaButton = findViewById<Button>(R.id.CrearFacturaBt)
        crearFacturaButton.setOnClickListener {
            val intent = Intent(this, CrearFacturaActivity::class.java)
            startActivityForResult(intent, 2)
        }

        val regresarButton = findViewById<Button>(R.id.RegresarBt)
        regresarButton.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == RESULT_OK) {
            updateListView()
        }
    }

    private fun loadFacturas(): List<String> {
        return gestorSQL.getFacturas().map { it.detalle + " - " + it.ubicacion }
    }

    private fun updateListView() {
        adapter.clear()
        adapter.addAll(loadFacturas())
        adapter.notifyDataSetChanged()
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
                gestorSQL.deleteFactura(gestorSQL.getFacturas()[info.position].id)
                updateListView()
            }
            R.id.map -> showMap(gestorSQL.getFacturas()[info.position].ubicacion)
            else -> return super.onContextItemSelected(item)
        }
        return true
    }

    private fun editFactura(position: Int) {
        val factura = gestorSQL.getFacturas()[position]
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar Factura")

        val input = EditText(this)
        input.setText(factura.detalle + " - " + factura.ubicacion)
        builder.setView(input)

        builder.setPositiveButton("Guardar") { dialog, which ->
            val parts = input.text.toString().split(" - ")
            if (parts.size >= 2) {
                gestorSQL.updateFactura(factura.id, parts[0], parts[1])
                updateListView()
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, which -> dialog.cancel() }

        builder.show()
    }

    private fun showMap(ubicacion: String) {
        val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(ubicacion)}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        } else {
            Toast.makeText(this, "Google Maps no está instalado.", Toast.LENGTH_SHORT).show()
        }
    }
}
