private fun rupiah(nominal: Double): String {
    val str = nominal.toLong().toString()
    val builder = StringBuilder()
    for (i in str.indices) {
        if (i > 0 && (str.length - i) % 3 == 0) builder.append('.')
        builder.append(str[i])
    }
    return "Rp $builder"
}

private fun tampilkanOpsiPengiriman(distanceKm: Double) {
    println("📍 Jarak $distanceKm km:")
    val options = ShippingOptions.availableFor(distanceKm)
    if (options.isEmpty()) {
        println("   ❌ Tidak ada opsi pengiriman (di luar jangkauan 40 km)")
        return
    }
    options.forEach {
        println("   • ${it.name.padEnd(9)} ${rupiah(it.calculateCost(distanceKm)).padEnd(11)} est. ${it.estimation}")
    }
}

fun main() {
    val toko = ECommerceSystem("Toko Online Polman")

    // --- Produk ---
    val laptop = ElectronicProduct("E001", "Laptop", 10_000_000.0, 10, "Asus", 36, true)
    val kaos = ClothingProduct("C001", "Kaos Polos", 100_000.0, 20, "XL", "Katun", false)
    val beras = FoodProduct("F001", "Beras Organik", 50_000.0, 30, "2027-12-31", 5000.0, true)
    listOf(laptop, kaos, beras).forEach { toko.addProduct(it) }
    println()

    // --- User ---
    toko.registerUser("sean", "sean@mail.com", "rahasia123")
    toko.registerUser("martin", "martin@mail.com", "martin123")
    val sean = toko.findUser("sean")!!
    val martin = toko.findUser("martin")!!
    println()

    // --- Opsi pengiriman berdasarkan jarak ---
    println("=== OPSI PENGIRIMAN ===")
    listOf(5.0, 15.0, 25.0, 40.0, 55.0).forEach { tampilkanOpsiPengiriman(it) }
    println()

    val qris = QRISPayment("QRIS-1234567890", "MERCHANT-01")

    // --- Skenario 1: Instant, 8 km ---
    println("=== SKENARIO 1: INSTANT (8 km) ===")
    sean.getCart().addItem(kaos, 2)
    sean.getCart().addItem(beras, 1)
    sean.checkout(qris, InstantShipping(), 8.0)?.let {
        it.updateStatus(OrderStatus.Paid)
        toko.addOrder(it)
        it.displayOrder()
    }
    println()

    // --- Skenario 2: Same Day, 30 km ---
    println("=== SKENARIO 2: SAME DAY (30 km) ===")
    martin.getCart().addItem(laptop, 1)
    martin.checkout(qris, SameDayShipping(), 30.0)?.let {
        it.updateStatus(OrderStatus.Paid)
        toko.addOrder(it)
        it.displayOrder()
    }
    println()

    // --- Skenario 3: Instant di luar jangkauan (20 km) -> ditolak ---
    println("=== SKENARIO 3: INSTANT (20 km) - DI LUAR JANGKAUAN ===")
    val james = toko.findUser("james") ?: run {
        toko.registerUser("james", "james@mail.com", "james123")
        toko.findUser("james")!!
    }
    james.getCart().addItem(beras, 3)
    val gagal = james.checkout(qris, InstantShipping(), 20.0)
    if (gagal == null) {
        println("↪ Coba lagi dengan Same Day...")
        james.checkout(qris, SameDayShipping(), 20.0)?.let {
            it.updateStatus(OrderStatus.Paid)
            toko.addOrder(it)
            it.displayOrder()
        }
    }
    println()

    // --- Laporan ---
    toko.displaySalesReport()
    sean.displayOrders()
    martin.displayOrders()
    james.displayOrders()
}
