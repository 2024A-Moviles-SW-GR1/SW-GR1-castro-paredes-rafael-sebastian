import java.util.Date

data class Factura(
    private var _idComprador: Int,
    private var _idFactura: Int,
    private var _codigoFactura: String,
    private var _fechaFactura: Date,
    private var _facturaPagada: Boolean,
    private var _valorFactura: Double
) {
    var idComprador: Int
        get() = _idComprador
        set(value) {
            _idComprador = value
        }

    var idFactura: Int
        get() = _idFactura
        set(value) {
            _idFactura = value
        }

    var codigoFactura: String
        get() = _codigoFactura
        set(value) {
            _codigoFactura = value
        }

    var fechaFactura: Date
        get() = _fechaFactura
        set(value) {
            _fechaFactura = value
        }

    var facturaPagada: Boolean
        get() = _facturaPagada
        set(value) {
            _facturaPagada = value
        }

    var valorFactura: Double
        get() = _valorFactura
        set(value) {
            _valorFactura = value
        }
}
