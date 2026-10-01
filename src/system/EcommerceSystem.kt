class ECommerceSystem(val name: String = "Toko Online") {
    // ============================================================
    // PROPERTI
    // ============================================================

    private val products = mutableListOf<Product>()
    private val users = mutableListOf<User>()
    private val orders = mutableListOf<Order>()

    // ============================================================
    // MANAJEMEN PRODUK
    // ============================================================

    fun addProduct(product: Product) {
        products.add(product)
        println("✅ Produk ${product.name} ditambahkan")
    }

    fun searchProduct(keyword: String): List<Product> {
        return products.filter {
            it.name.lowercase().contains(keyword.lowercase()) ||
                    it.id.lowercase().contains(keyword.lowercase())
        }
    }

    fun findProductById(id: String): Product? {
        return products.find { it.id == id }
    }

    fun displayAllProducts() {
        println("=" .repeat(55))
        println("📦 SEMUA PRODUK")
        println("=" .repeat(55))
        println("Total: ${products.size} produk")
        println("-" .repeat(55))

        products.forEach { product ->
            product.displayInfo()
        }
    }

    // ============================================================
    // MANAJEMEN USER
    // ============================================================

    fun registerUser(username: String, email: String, password: String): Boolean {
        if (users.any { it.username == username }) {
            println("❌ Username $username sudah digunakan")
            return false
        }
        val user = User(username, email, password)
        users.add(user)
        println("✅ User $username berhasil didaftarkan")
        return true
    }

    fun findUser(username: String): User? {
        return users.find { it.username == username }
    }

    // ============================================================
    // MANAJEMEN ORDER
    // ============================================================

    fun addOrder(order: Order) {
        orders.add(order)
    }

    fun getTotalRevenue(): Double {
        return orders.sumOf { it.totalPrice }
    }

    fun getOrderCount(): Int = orders.size

    fun displaySalesReport() {
        println("=" .repeat(55))
        println("📊 LAPORAN PENJUALAN")
        println("=" .repeat(55))
        println("Total Order   : ${getOrderCount()}")
        println("Total Revenue : Rp ${formatRupiah(getTotalRevenue())}")
        println("-" .repeat(55))

        if (orders.isEmpty()) {
            println("   Belum ada penjualan")
        } else {
            orders.forEachIndexed { index, order ->
                println("${index + 1}. ${order.id} - ${order.customerName}")
                println("   Status: ${order.status.display()}")
                println("   Total: Rp ${formatRupiah(order.totalPrice)}")
            }
        }
        println("=" .repeat(55))
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