package com.example.aplicacionexamen

data class Factura(
    val id: Int,
    val detalle: String,
    val ubicacion: String,
    val clienteId: Int
)