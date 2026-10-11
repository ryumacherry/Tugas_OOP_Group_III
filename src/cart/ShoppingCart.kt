class ShoppingCart(private val owner: String) {
    // ============================================================
    // PROPERTI
    // ============================================================

    private val items = mutableMapOf<Product, Int>()  // Product → Quantity
    var totalItems: Int = 0
        private set

    // ============================================================
    // METODE
    // ============================================================

    fun addItem(product: Product, quantity: Int): Boolean {
        if (quantity <= 0) {
            println("❌ Jumlah harus lebih dari 0")
            return false
        }

        if (!product.reduceStock(quantity)) {
            println("❌ Stok tidak mencukupi (tersedia: ${product.stock})")
            return false
        }

        items[product] = items.getOrDefault(product, 0) + quantity
        totalItems += quantity
        println("✅ ${product.name} x$quantity ditambahkan ke keranjang")
        return true
    }

    fun removeItem(product: Product): Boolean {
        val quantity = items[product] ?: return false
        items.remove(product)
        totalItems -= quantity
        // Kembalikan stok
        product.reduceStock(-quantity)
        println("✅ ${product.name} dihapus dari keranjang")
        return true
    }

    fun getTotalPrice(): Double {
        return items.entries.sumOf { (product, quantity) ->
            product.getDiscountedPrice() * quantity
        }
    }

    fun getTotalDiscount(): Double {
        return items.entries.sumOf { (product, quantity) ->
            product.calculateDiscount() * quantity
        }
    }

    fun getItems(): Map<Product, Int> = items

    fun isEmpty(): Boolean = items.isEmpty()

    fun displayCart() {
        println("=" .repeat(50))
        println("🛒 KERANJANG BELANJA - $owner")
        println("=" .repeat(50))

        if (items.isEmpty()) {
            println("   Keranjang kosong")
        } else {
            items.forEach { (product, quantity) ->
                println("${product.name} x$quantity = Rp ${formatRupiah(product.getDiscountedPrice() * quantity)}")
                println("   (Diskon: Rp ${formatRupiah(product.calculateDiscount() * quantity)})")
            }
            println("-" .repeat(50))
            println("Total Diskon : Rp ${formatRupiah(getTotalDiscount())}")
            println("Total Belanja: Rp ${formatRupiah(getTotalPrice())}")
        }
        println("=" .repeat(50))
    }

    private fun formatRupiah(nominal: Double): String {
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