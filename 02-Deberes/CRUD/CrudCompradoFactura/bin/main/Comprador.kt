import java.util.*

data class Comprador(
    private var _idComprador: Int,
    private var _nombre: String,
    private var _fechaNacimiento: Date,
    private var _saldoCuenta: Double,
    private var _esCompradorVip: Boolean
) {
    var idComprador: Int
        get() = _idComprador
        set(value) {
            _idComprador = value
        }

    var nombre: String
        get() = _nombre
        set(value) {
            _nombre = value
        }

    var fechaNacimiento: Date
        get() = _fechaNacimiento
        set(value) {
            _fechaNacimiento = value
        }

    var saldoCuenta: Double
        get() = _saldoCuenta
        set(value) {
            _saldoCuenta = value
        }

    var esCompradorVip: Boolean
        get() = _esCompradorVip
        set(value) {
            _esCompradorVip = value
        }
}
