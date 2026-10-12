class BankTransferPayment(
    private val bankName: String,
    private val accountNumber: String
) : PaymentMethod {
    override val name = "Transfer Bank"

    override fun getFee(amount: Double): Double {
        val fee = amount * 0.01  // 1%
        return maxOf(fee, 5000.0)  // Minimal Rp 5.000
    }

    override fun processPayment(amount: Double): PaymentResult {
        if (accountNumber.length < 8) {
            return PaymentResult.Failed("Nomor rekening tidak valid", 404)
        }
        return PaymentResult.Success("BT-${System.currentTimeMillis()}")
    }
}