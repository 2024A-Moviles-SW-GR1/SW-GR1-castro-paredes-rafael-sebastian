package com.example.aplicacionexamen

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacionexamen.GestorSQL

class CrearClienteActivity : AppCompatActivity() {
    private lateinit var gestorSQL: GestorSQL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cliente)

        gestorSQL = GestorSQL(this)  // Instancia de GestorSQL

        val nombreEditText = findViewById<EditText>(R.id.NombreCliente)
        val apellidoEditText = findViewById<EditText>(R.id.ApellidoCliente)
        val guardarButton = findViewById<Button>(R.id.guardarCliente)

        guardarButton.setOnClickListener {
            val nombre = nombreEditText.text.toString().trim()
            val apellido = apellidoEditText.text.toString().trim()

            // Guardar directamente en la base de datos
            val id = gestorSQL.addCliente(nombre, apellido)
            if (id > 0) {
                setResult(RESULT_OK)  // Indica que el cliente fue creado con éxito
            } else {
                setResult(RESULT_CANCELED)  // Indica que hubo un error
            }
            finish()
        }
    }
}
