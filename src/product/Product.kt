abstract class Product(
    val id: String,
    val name: String,
    private var price: Double,
    var stock: Int
) {
    // ============================================================
    // PROPERTI
    // ============================================================

    // Akses harga dasar (price tetap private)
    fun getBasePrice(): Double = price

    // Getter untuk price dengan format
    val formattedPrice: String
        get() = "Rp ${formatRupiah(price)}"

    // ============================================================
    // METODE ABSTRAK
    // ============================================================

    // Menghitung diskon — harus diimplementasikan oleh subclass
    abstract fun calculateDiscount(): Double

    // Mendapatkan kategori produk
    abstract fun getCategory(): String

    // ============================================================
    // METODE CONCRETE (Bisa digunakan semua subclass)
    // ============================================================

    // Harga setelah diskon
    open fun getDiscountedPrice(): Double {
        return price - calculateDiscount()
    }

    // Menampilkan info produk
    open fun displayInfo() {
        println("=" .repeat(50))
        println("📦 ${getCategory()} - $name")
        println("ID        : $id")
        println("Harga     : $formattedPrice")
        println("Diskon    : Rp ${formatRupiah(calculateDiscount())}")
        println("Harga Akhir: Rp ${formatRupiah(getDiscountedPrice())}")
        println("Stok      : $stock")
        println("=" .repeat(50))
    }

    // Mengurangi stok
    fun reduceStock(quantity: Int): Boolean {
        return if (stock >= quantity) {
            stock -= quantity
            true
        } else {
            false
        }
    }

    // ============================================================
    // HELPER METHOD (Protected)
    // ============================================================

    protected fun formatRupiah(nominal: Double): String {
        val str = nominal.toLong().toString()
        val builder = StringBuilder()
        var count = 0
        for (i in str.length - 1 downTo 0) {
            builder.insert(0, str[i])
            count++
            if (count % 3 == 0 && i > 0) {
                builder.insert(0, ".")
            }
        }
        return builder.toString()
    }
}