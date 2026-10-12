class QRISPayment(
    private val qrCode: String,
    private val merchantId: String
) : PaymentMethod {
    override val name = "QRIS"

    override fun getFee(amount: Double): Double {
        return amount * 0.005  // 0.5% fee
    }

    override fun processPayment(amount: Double): PaymentResult {
        if (qrCode.length < 10) {
            return PaymentResult.Failed("Kode QR tidak valid", 403)
        }
        return PaymentResult.Success("QR-${System.currentTimeMillis()}")
    }
}