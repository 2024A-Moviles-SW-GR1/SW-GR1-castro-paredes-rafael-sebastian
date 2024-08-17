import java.io.File
import java.text.SimpleDateFormat

fun main() {
    val archivoComprador = "compradores.csv"
    val archivoFactura = "facturas.csv"
    val archivoManager = ArchivoManager()

    // Crear encabezados si no existen
    if (!File(archivoComprador).exists()) {
        File(archivoComprador).writeText("idComprador,nombre,fechaNacimiento,saldoCuenta,esCompradorVip\n")
    }
    if (!File(archivoFactura).exists()) {
        File(archivoFactura).writeText("idComprador,idFactura,codigoFactura,fechaFactura,facturaPagada,valorFactura\n")
    }

    while (true) {
        println("1. CRUD Comprador")
        println("2. CRUD Factura")
        println("3. Ver Facturas por Comprador")
        println("4. Salir")
        when (readLine()?.toIntOrNull()) {
            1 -> menuComprador(archivoComprador, archivoManager)
            2 -> menuFactura(archivoFactura, archivoManager)
            3 -> {
                println("Ingrese ID del comprador:")
                val idComprador = readLine()?.toIntOrNull() ?: return
                archivoManager.verFacturasPorComprador(idComprador, archivoComprador, archivoFactura)
            }
            4 -> break
            else -> println("Opción no válida")
        }
    }
}

fun menuComprador(archivo: String, archivoManager: ArchivoManager) {
    while (true) {
        println("1. Crear Comprador")
        println("2. Leer Comprador")
        println("3. Actualizar Comprador")
        println("4. Eliminar Comprador")
        println("5. Volver")
        when (readLine()?.toIntOrNull()) {
            1 -> {
                println("Ingrese los datos del comprador:")
                println("ID:")
                val id = readLine()?.toIntOrNull() ?: return
                println("Nombre:")
                val nombre = readLine() ?: return
                println("Fecha de Nacimiento (dd-MM-yyyy):")
                val fecha = SimpleDateFormat("dd-MM-yyyy").parse(readLine() ?: return)
                println("Saldo Cuenta:")
                val saldo = readLine()?.toDoubleOrNull() ?: return
                println("Es Comprador VIP (true/false):")
                val vip = readLine()?.toBoolean() ?: return
                archivoManager.crearComprador(Comprador(id, nombre, fecha, saldo, vip), archivo)
            }
            2 -> {
                println("Ingrese ID del comprador:")
                val id = readLine()?.toIntOrNull() ?: return
                val comprador = archivoManager.leerComprador(id, archivo)
                if (comprador != null) {
                    println(comprador)
                } else {
                    println("Comprador no encontrado.")
                }
            }
            3 -> {
                println("Ingrese los datos del comprador a actualizar:")
                println("ID:")
                val id = readLine()?.toIntOrNull() ?: return
                println("Nombre:")
                val nombre = readLine() ?: return
                println("Fecha de Nacimiento (dd-MM-yyyy):")
                val fecha = SimpleDateFormat("dd-MM-yyyy").parse(readLine() ?: return)
                println("Saldo Cuenta:")
                val saldo = readLine()?.toDoubleOrNull() ?: return
                println("Es Comprador VIP (true/false):")
                val vip = readLine()?.toBoolean() ?: return
                archivoManager.actualizarComprador(Comprador(id, nombre, fecha, saldo, vip), archivo)
            }
            4 -> {
                println("Ingrese ID del comprador a eliminar:")
                val id = readLine()?.toIntOrNull() ?: return
                archivoManager.eliminarComprador(id, archivo)
            }
            5 -> return
            else -> println("Opción no válida")
        }
    }
}

fun menuFactura(archivo: String, archivoManager: ArchivoManager) {
    while (true) {
        println("1. Crear Factura")
        println("2. Leer Factura")
        println("3. Actualizar Factura")
        println("4. Eliminar Factura")
        println("5. Volver")
        when (readLine()?.toIntOrNull()) {
            1 -> {
                println("Ingrese los datos de la factura:")
                println("ID Comprador:")
                val idComprador = readLine()?.toIntOrNull() ?: return
                println("ID Factura:")
                val idFactura = readLine()?.toIntOrNull() ?: return
                println("Código Factura:")
                val codigoFactura = readLine() ?: return
                println("Fecha Factura (dd-MM-yyyy):")
                val fecha = SimpleDateFormat("dd-MM-yyyy").parse(readLine() ?: return)
                println("Factura Pagada (true/false):")
                val pagada = readLine()?.toBoolean() ?: return
                println("Valor Factura:")
                val valor = readLine()?.toDoubleOrNull() ?: return
                archivoManager.crearFactura(Factura(idComprador, idFactura, codigoFactura, fecha, pagada, valor), archivo)
            }
            2 -> {
                println("Ingrese ID de la factura:")
                val id = readLine()?.toIntOrNull() ?: return
                val factura = archivoManager.leerFactura(id, archivo)
                if (factura != null) {
                    println(factura)
                } else {
                    println("Factura no encontrada.")
                }
            }
            3 -> {
                println("Ingrese los datos de la factura a actualizar:")
                println("ID Comprador:")
                val idComprador = readLine()?.toIntOrNull() ?: return
                println("ID Factura:")
                val idFactura = readLine()?.toIntOrNull() ?: return
                println("Código Factura:")
                val codigoFactura = readLine() ?: return
                println("Fecha Factura (dd-MM-yyyy):")
                val fecha = SimpleDateFormat("dd-MM-yyyy").parse(readLine() ?: return)
                println("Factura Pagada (true/false):")
                val pagada = readLine()?.toBoolean() ?: return
                println("Valor Factura:")
                val valor = readLine()?.toDoubleOrNull() ?: return
                archivoManager.actualizarFactura(Factura(idComprador, idFactura, codigoFactura, fecha, pagada, valor), archivo)
            }
            4 -> {
                println("Ingrese ID de la factura a eliminar:")
                val id = readLine()?.toIntOrNull() ?: return
                archivoManager.eliminarFactura(id, archivo)
            }
            5 -> return
            else -> println("Opción no válida")
        }
    }
}
