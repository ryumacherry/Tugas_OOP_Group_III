class ClothingProduct(
    id: String,
    name: String,
    price: Double,
    stock: Int,
    val size: String,
    val material: String,
    val isSeasonal: Boolean  // Seasonal mendapat diskon khusus
) : Product(id, name, price, stock) {

    override fun calculateDiscount(): Double {
        var discount = 0.0

        // Diskon dasar: 10% untuk semua pakaian
        discount += getBasePrice() * 0.10

        // Diskon tambahan: 15% jika seasonal
        if (isSeasonal) {
            discount += getBasePrice() * 0.15
        }

        // Diskon tambahan: 5% untuk size XL atau lebih besar
        if (size.uppercase() in listOf("XL", "XXL", "XXXL")) {
            discount += getBasePrice() * 0.05
        }

        return discount
    }

    override fun getCategory(): String = "Pakaian"

    override fun displayInfo() {
        super.displayInfo()
        println("Ukuran    : $size")
        println("Bahan     : $material")
        println("Seasonal  : ${if (isSeasonal) "✅ Ya" else "❌ Tidak"}")
        println("=" .repeat(50))
    }
}
