class User(
    val username: String,
    val email: String,
    private val password: String  // Encapsulated!
) {
    // ============================================================
    // PROPERTI
    // ============================================================

    private val orders = mutableListOf<Order>()
    private val cart = ShoppingCart(username)

    val orderCount: Int
        get() = orders.size

    val totalSpent: Double
        get() = orders.sumOf { it.totalPrice }

    // ============================================================
    // METODE
    // ============================================================

    fun authenticate(inputPassword: String): Boolean {
        return password == inputPassword
    }

    fun getCart(): ShoppingCart = cart

    fun checkout(
        paymentMethod: PaymentMethod,
        shippingMethod: ShippingMethod? = null,
        distanceKm: Double = 0.0
    ): Order? {
        if (cart.isEmpty()) {
            println("❌ Keranjang kosong")
            return null
        }

        if (shippingMethod != null && !shippingMethod.isAvailable(distanceKm)) {
            println("❌ ${shippingMethod.name} tidak tersedia untuk jarak $distanceKm km " +
                    "(jangkauan ${shippingMethod.minDistanceKm.toInt()}-${shippingMethod.maxDistanceKm.toInt()} km)")
            return null
        }

        val shippingCost = shippingMethod?.calculateCost(distanceKm) ?: 0.0
        val amount = cart.getTotalPrice() + shippingCost
        println("💳 Memproses pembayaran dengan ${paymentMethod.name}...")

        val result = paymentMethod.processPayment(amount)
        when (result) {
            is PaymentResult.Success -> {
                println("✅ Pembayaran berhasil! ID: ${result.transactionId}")
                val order = createOrder(shippingMethod, distanceKm)
                orders.add(order)
                println("✅ Order ${order.id} berhasil dibuat")
                return order
            }
            is PaymentResult.Failed -> {
                println("❌ Pembayaran gagal: ${result.reason}")
                return null
            }
            PaymentResult.Pending -> {
                println("⏳ Pembayaran pending...")
                // Kembalikan stok
                // ...
                return null
            }
        }
    }

    private fun createOrder(shippingMethod: ShippingMethod?, distanceKm: Double): Order {
        val orderId = "ORD-${System.currentTimeMillis()}"
        val items = cart.getItems()
        val order = Order(orderId, username, items, OrderStatus.Pending, shippingMethod, distanceKm)
        // Kosongkan keranjang
        // cart.clear()
        return order
    }

    fun displayOrders() {
        println("=" .repeat(50))
        println("📋 RIWAYAT ORDER - $username")
        println("=" .repeat(50))

        if (orders.isEmpty()) {
            println("   Belum ada order")
        } else {
            orders.forEachIndexed { index, order ->
                println("${index + 1}. Order ${order.id} - ${order.status.display()}")
                println("   Total: Rp ${formatRupiah(order.totalPrice)}")
            }
        }
        println("Total Belanja: Rp ${formatRupiah(totalSpent)}")
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