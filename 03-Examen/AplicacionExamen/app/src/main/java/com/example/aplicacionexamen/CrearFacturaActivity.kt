package com.example.aplicacionexamen

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacionexamen.GestorSQL

class CrearFacturaActivity : AppCompatActivity() {
    private lateinit var gestorSQL: GestorSQL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_factura)

        gestorSQL = GestorSQL(this)  // Instancia de GestorSQL

        val detalleEditText = findViewById<EditText>(R.id.crearFactura)
        val ubicacionEditText = findViewById<EditText>(R.id.UbicacionFactura)
        val guardarFacturaButton = findViewById<Button>(R.id.GuardarFacturaBt)
        val clienteId = intent.getIntExtra("clienteId", 0)  // Asumiendo que el clienteId es pasado a esta actividad

        guardarFacturaButton.setOnClickListener {
            val detalle = detalleEditText.text.toString().trim()
            val ubicacion = ubicacionEditText.text.toString().trim()

            // Guardar directamente en la base de datos
            val id = gestorSQL.addFactura(detalle, ubicacion, clienteId)
            if (id > 0) {
                setResult(RESULT_OK)  // Indica que la factura fue creada con éxito
            } else {
                setResult(RESULT_CANCELED)  // Indica que hubo un error
            }
            finish()
        }
    }
}
