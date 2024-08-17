package com.example.aplicacionmovil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CrearClienteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cliente)

        val nombreEditText = findViewById<EditText>(R.id.NombreCliente)
        val apellidoEditText = findViewById<EditText>(R.id.ApellidoCliente)
        val guardarButton = findViewById<Button>(R.id.guardarCliente)

        guardarButton.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val apellido = apellidoEditText.text.toString()
            val resultadoIntent = Intent()
            resultadoIntent.putExtra("cliente", "$nombre $apellido")
            setResult(RESULT_OK, resultadoIntent)
            finish()
        }
    }
}