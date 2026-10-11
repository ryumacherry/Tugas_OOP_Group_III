class ElectronicProduct(
    id: String,
    name: String,
    price: Double,
    stock: Int,
    val brand: String,
    val warrantyMonths: Int,  // Durasi garansi dalam bulan
    val isPremium: Boolean    // Premium mendapat diskon ekstra
) : Product(id, name, price, stock) {

    override fun calculateDiscount(): Double {
        var discount = 0.0

        // Diskon dasar: 5% untuk semua elektronik
        discount += getBasePrice() * 0.05

        // Diskon tambahan: 10% jika premium
        if (isPremium) {
            discount += getBasePrice() * 0.10
        }

        // Diskon tambahan: 5% jika garansi > 24 bulan
        if (warrantyMonths > 24) {
            discount += getBasePrice() * 0.05
        }

        return discount
    }

    override fun getCategory(): String = "Elektronik"

    override fun displayInfo() {
        super.displayInfo()
        println("Merek     : $brand")
        println("Garansi   : $warrantyMonths bulan")
        println("Premium   : ${if (isPremium) "✅ Ya" else "❌ Tidak"}")
        println("=" .repeat(50))
    }
}