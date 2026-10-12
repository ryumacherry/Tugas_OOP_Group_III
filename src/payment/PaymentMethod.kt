interface PaymentMethod {
    val name: String
    fun processPayment(amount: Double): PaymentResult
    fun getFee(amount: Double): Double
}

// Sealed class untuk hasil pembayaran
sealed class PaymentResult {
    data class Success(val transactionId: String) : PaymentResult()
    data class Failed(val reason: String, val errorCode: Int) : PaymentResult()
    object Pending : PaymentResult()
}