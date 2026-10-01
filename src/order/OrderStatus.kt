sealed class OrderStatus {
    object Pending : OrderStatus() {
        override fun display() = "⏳ Menunggu Pembayaran"
    }
    object Paid : OrderStatus() {
        override fun display() = "✅ Dibayar"
    }
    object Shipped : OrderStatus() {
        override fun display() = "🚚 Dikirim"
    }
    object Delivered : OrderStatus() {
        override fun display() = "📦 Diterima"
    }
    data class Cancelled(val reason: String) : OrderStatus() {
        override fun display() = "❌ Dibatalkan: $reason"
    }

    abstract fun display(): String

    fun isFinal(): Boolean {
        return this is Delivered || this is Cancelled
    }
}