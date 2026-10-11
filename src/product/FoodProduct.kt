class FoodProduct(
    id: String,
    name: String,
    price: Double,
    stock: Int,
    val expiryDate: String,
    val weight: Double,  // dalam gram
    val isOrganic: Boolean
) : Product(id, name, price, stock) {

    override fun calculateDiscount(): Double {
        var discount = 0.0

        // Diskon dasar: 5% untuk semua makanan
        discount += getBasePrice() * 0.05

        // Diskon tambahan: 20% jika organik
        if (isOrganic) {
            discount += getBasePrice() * 0.20
        }

        return discount
    }

    override fun getCategory(): String = "Makanan"

    override fun displayInfo() {
        super.displayInfo()
        println("Berat     : $weight gram")
        println("Kadaluarsa: $expiryDate")
        println("Organik   : ${if (isOrganic) "✅ Ya" else "❌ Tidak"}")
        println("=" .repeat(50))
    }
}