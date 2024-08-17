import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ArchivoManager {

    private val dateFormat = SimpleDateFormat("dd-MM-yyyy")

    fun guardarComprador(comprador: Comprador, archivo: String) {
        File(archivo).appendText("${comprador.idComprador},${comprador.nombre},${dateFormat.format(comprador.fechaNacimiento)},${comprador.saldoCuenta},${comprador.esCompradorVip}\n")
    }

    fun guardarFactura(factura: Factura, archivo: String) {
        File(archivo).appendText("${factura.idComprador},${factura.idFactura},${factura.codigoFactura},${dateFormat.format(factura.fechaFactura)},${factura.facturaPagada},${factura.valorFactura}\n")
    }

    fun leerCompradores(archivo: String): List<Comprador> {
        return File(archivo).readLines().drop(1).map { linea ->
            val partes = linea.split(",")
            Comprador(
                partes[0].toInt(),
                partes[1],
                dateFormat.parse(partes[2]),
                partes[3].toDouble(),
                partes[4].toBoolean()
            )
        }
    }

    fun leerFacturas(archivo: String): List<Factura> {
        return File(archivo).readLines().drop(1).map { linea ->
            val partes = linea.split(",")
            Factura(
                partes[0].toInt(),
                partes[1].toInt(),
                partes[2],
                dateFormat.parse(partes[3]),
                partes[4].toBoolean(),
                partes[5].toDouble()
            )
        }
    }

    fun crearComprador(comprador: Comprador, archivo: String) {
        guardarComprador(comprador, archivo)
    }

    fun leerComprador(id: Int, archivo: String): Comprador? {
        return leerCompradores(archivo).find { it.idComprador == id }
    }

    fun actualizarComprador(comprador: Comprador, archivo: String) {
        val compradores = leerCompradores(archivo).toMutableList()
        val index = compradores.indexOfFirst { it.idComprador == comprador.idComprador }
        if (index != -1) {
            compradores[index] = comprador
            File(archivo).writeText("idComprador,nombre,fechaNacimiento,saldoCuenta,esCompradorVip\n")
            compradores.forEach { guardarComprador(it, archivo) }
        }
    }

    fun eliminarComprador(id: Int, archivo: String) {
        val compradores = leerCompradores(archivo).filter { it.idComprador != id }
        File(archivo).writeText("idComprador,nombre,fechaNacimiento,saldoCuenta,esCompradorVip\n")
        compradores.forEach { guardarComprador(it, archivo) }
    }

    fun crearFactura(factura: Factura, archivo: String) {
        guardarFactura(factura, archivo)
    }

    fun leerFactura(id: Int, archivo: String): Factura? {
        return leerFacturas(archivo).find { it.idFactura == id }
    }

    fun actualizarFactura(factura: Factura, archivo: String) {
        val facturas = leerFacturas(archivo).toMutableList()
        val index = facturas.indexOfFirst { it.idFactura == factura.idFactura }
        if (index != -1) {
            facturas[index] = factura
            File(archivo).writeText("idComprador,idFactura,codigoFactura,fechaFactura,facturaPagada,valorFactura\n")
            facturas.forEach { guardarFactura(it, archivo) }
        }
    }

    fun eliminarFactura(id: Int, archivo: String) {
        val facturas = leerFacturas(archivo).filter { it.idFactura != id }
        File(archivo).writeText("idComprador,idFactura,codigoFactura,fechaFactura,facturaPagada,valorFactura\n")
        facturas.forEach { guardarFactura(it, archivo) }
    }

    fun verFacturasPorComprador(idComprador: Int, archivoComprador: String, archivoFactura: String) {
        val comprador = leerComprador(idComprador, archivoComprador)
        if (comprador != null) {
            println("Facturas asociadas al comprador ${comprador.nombre}:")
            val facturas = leerFacturas(archivoFactura).filter { it.idComprador == idComprador }
            if (facturas.isEmpty()) {
                println("No se encontraron facturas para este comprador.")
            } else {
                facturas.forEach { println(it) }
            }
        } else {
            println("Comprador no encontrado.")
        }
    }
}
