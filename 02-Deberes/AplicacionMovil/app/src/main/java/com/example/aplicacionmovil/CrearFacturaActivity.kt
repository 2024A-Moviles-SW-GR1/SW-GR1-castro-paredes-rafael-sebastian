package com.example.aplicacionmovil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CrearFacturaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_factura)

        val facturaEditText = findViewById<EditText>(R.id.crearFactura)
        val guardarFacturaButton = findViewById<Button>(R.id.GuardarFacturaBt)

        guardarFacturaButton.setOnClickListener {
            val factura = facturaEditText.text.toString()
            val resultadoIntent = Intent()
            resultadoIntent.putExtra("factura", factura)
            setResult(RESULT_OK, resultadoIntent)
            finish()
        }
    }
}