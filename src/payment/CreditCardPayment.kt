class CreditCardPayment(
    private val cardNumber: String,
    private val expiryDate: String,
    private val cvv: String
) : PaymentMethod {
    override val name = "Kartu Kredit"

    override fun getFee(amount: Double): Double {
        return amount * 0.02  // 2% fee
    }

    override fun processPayment(amount: Double): PaymentResult {
        if (cardNumber.length < 16) {
            return PaymentResult.Failed("Nomor kartu tidak valid", 401)
        }
        if (cvv.length != 3) {
            return PaymentResult.Failed("CVV tidak valid", 402)
        }
        return PaymentResult.Success("CC-${System.currentTimeMillis()}")
    }
}
