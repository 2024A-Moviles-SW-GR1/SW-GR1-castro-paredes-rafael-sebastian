package com.example.aplicacionexamen
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.aplicacionexamen.Cliente
import com.example.aplicacionexamen.Factura

class GestorSQL(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "AppDatabase.db"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_CLIENTE = """
            CREATE TABLE Cliente (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                apellido TEXT
            )
        """

        private const val SQL_CREATE_TABLE_FACTURA = """
            CREATE TABLE Factura (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                detalle TEXT,
                ubicacion TEXT,
                clienteId INTEGER,
                FOREIGN KEY(clienteId) REFERENCES Cliente(id)
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_CLIENTE)
        db.execSQL(SQL_CREATE_TABLE_FACTURA)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Aquí puedes manejar las actualizaciones de la base de datos
    }

    // CRUD Operaciones para Cliente
    fun addCliente(nombre: String, apellido: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", nombre)
            put("apellido", apellido)
        }
        return db.insert("Cliente", null, values)
    }

    fun getClientes(): MutableList<Cliente> {
        val db = this.readableDatabase
        val projection = arrayOf("id", "nombre", "apellido")
        val cursor = db.query("Cliente", projection, null, null, null, null, null)
        val clientes = mutableListOf<Cliente>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val nombre = getString(getColumnIndexOrThrow("nombre"))
                val apellido = getString(getColumnIndexOrThrow("apellido"))
                clientes.add(Cliente(id, nombre, apellido))
            }
        }
        cursor.close()
        return clientes
    }

    fun updateCliente(id: Int, nombre: String, apellido: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", nombre)
            put("apellido", apellido)
        }
        return db.update("Cliente", values, "id=?", arrayOf(id.toString()))
    }

    fun deleteCliente(id: Int): Int {
        val db = this.writableDatabase
        return db.delete("Cliente", "id=?", arrayOf(id.toString()))
    }

    // CRUD Operaciones para Factura
    fun addFactura(detalle: String, ubicacion: String, clienteId: Int): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("detalle", detalle)
            put("ubicacion", ubicacion)
            put("clienteId", clienteId)
        }
        return db.insert("Factura", null, values)
    }

    fun getFacturas(): MutableList<Factura> {
        val db = this.readableDatabase
        val projection = arrayOf("id", "detalle", "ubicacion", "clienteId")
        val cursor = db.query("Factura", projection, null, null, null, null, null)
        val facturas = mutableListOf<Factura>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val detalle = getString(getColumnIndexOrThrow("detalle"))
                val ubicacion = getString(getColumnIndexOrThrow("ubicacion"))
                val clienteId = getInt(getColumnIndexOrThrow("clienteId"))
                facturas.add(Factura(id, detalle, ubicacion, clienteId))
            }
        }
        cursor.close()
        return facturas
    }

    fun updateFactura(id: Int, detalle: String, ubicacion: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("detalle", detalle)
            put("ubicacion", ubicacion)
        }
        return db.update("Factura", values, "id=?", arrayOf(id.toString()))
    }

    fun deleteFactura(id: Int): Int {
        val db = this.writableDatabase
        return db.delete("Factura", "id=?", arrayOf(id.toString()))
    }
}
