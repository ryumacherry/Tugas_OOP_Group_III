# 🛒 Tugas OOP Group III — Sistem E-Commerce (Kotlin)

Aplikasi e-commerce berbasis konsol yang dibuat dengan **Kotlin** untuk mendemonstrasikan konsep **Object-Oriented Programming (OOP)**. Fitur mencakup manajemen produk, keranjang belanja, checkout dengan berbagai metode pembayaran, dan pengiriman berdasarkan jarak.

## ✨ Fitur

- **Manajemen produk** — tambah, cari (nama/ID), dan tampilkan produk
- **Tiga kategori produk** dengan aturan diskon masing-masing: Elektronik, Pakaian, Makanan
- **Manajemen user** — registrasi, autentikasi, riwayat order
- **Keranjang belanja** — tambah/hapus item dengan pengecekan stok
- **Pembayaran** — Kartu Kredit, Transfer Bank, QRIS (lengkap dengan biaya masing-masing)
- **Status order** — Pending, Paid, Shipped, Delivered, Cancelled
- **Pengiriman** — opsi Instant dan Same Day berdasarkan jarak
- **Laporan penjualan** — total order dan pendapatan

## 🚚 Opsi Pengiriman

| Opsi | Jangkauan | Tarif | Estimasi |
|---|---|---|---|
| Instant | 0 – 15 km | Rp 10.000 + Rp 2.500/km | 1–3 jam |
| Same Day | 0 – 40 km | Rp 8.000 + Rp 1.500/km | 6–12 jam |

- Jarak dibulatkan ke atas untuk perhitungan tarif.
- Jika jarak di luar jangkauan metode yang dipilih, checkout ditolak.
- Jarak lebih dari 40 km tidak memiliki opsi pengiriman.

## 🧱 Konsep OOP yang Digunakan

| Konsep | Penerapan |
|---|---|
| **Abstraction** | `Product` (abstract class) dengan `calculateDiscount()` dan `getCategory()` abstrak |
| **Inheritance** | `ElectronicProduct`, `ClothingProduct`, `FoodProduct` mewarisi `Product` |
| **Polymorphism** | Tiap kategori menghitung diskon berbeda; interface `PaymentMethod` dan `ShippingMethod` punya banyak implementasi |
| **Encapsulation** | `price` di `Product` dan `password` di `User` bersifat `private`, diakses lewat metode publik |
| **Interface** | `PaymentMethod`, `ShippingMethod` |
| **Sealed class** | `OrderStatus`, `PaymentResult` |

## 📁 Struktur Proyek

```
src/
├── Main.kt                      # Titik masuk program (demo)
├── cart/
│   └── ShoppingCart.kt
├── order/
│   ├── Order.kt
│   └── OrderStatus.kt
├── payment/
│   ├── PaymentMethod.kt         # Interface + sealed class PaymentResult
│   ├── CreditCardPayment.kt
│   ├── BankTransferPayment.kt
│   └── QRISPayment.kt
├── product/
│   ├── Product.kt
│   ├── ElectronicProduct.kt
│   ├── ClothingProduct.kt
│   └── FoodProduct.kt
├── shipping/
│   ├── ShippingMethod.kt        # Interface + ShippingOptions
│   ├── InstantShipping.kt
│   └── SameDayShipping.kt
├── system/
│   └── EcommerceSystem.kt
└── user/
    └── User.kt
```

## 💸 Aturan Diskon

| Kategori | Diskon dasar | Tambahan |
|---|---|---|
| Elektronik | 5% | +10% jika premium, +5% jika garansi > 24 bulan |
| Pakaian | 10% | +15% jika seasonal, +5% jika ukuran XL/XXL/XXXL |
| Makanan | 5% | +20% jika organik |

## 💳 Biaya Pembayaran

| Metode | Biaya |
|---|---|
| Kartu Kredit | 2% |
| Transfer Bank | 1% (minimal Rp 5.000) |
| QRIS | 0,5% |

## ▶️ Cara Menjalankan

**Prasyarat:** JDK (disarankan 17 atau lebih baru) dan IntelliJ IDEA dengan plugin Kotlin.

1. Clone repository:
   ```bash
   git clone https://github.com/ryumacherry/Tugas_OOP_Group_III.git
   ```
2. Buka folder proyek di IntelliJ IDEA.
3. Pastikan folder `src` ditandai sebagai *Sources Root*.
4. Buka `src/Main.kt`, lalu klik ikon ▶️ di samping `fun main()`, atau klik kanan file dan pilih **Run 'MainKt'**.

**Lewat command line** (jika `kotlinc` sudah terpasang):
```bash
kotlinc $(find src -name '*.kt') -include-runtime -d ecommerce.jar
java -jar ecommerce.jar
```

## 🧪 Contoh Penggunaan

```kotlin
val toko = ECommerceSystem("Toko Online")
toko.registerUser("kuyun", "kuyun@mail.com", "rahasia123")
val user = toko.findUser("kuyun")!!

user.getCart().addItem(produk, 2)

val qris = QRISPayment("QRIS-1234567890", "MERCHANT-01")
val order = user.checkout(qris, InstantShipping(), distanceKm = 8.0)
order?.displayOrder()
```

Contoh keluaran untuk order dengan pengiriman Instant 8 km:

```
Total Harga : Rp 207.500
Pengiriman  : Instant (8.0 km, est. 1-3 jam)
Ongkir      : Rp 30.000
Total Bayar : Rp 237.500
```

## 👥 Anggota Kelompok

| Nama | NIM | Peran |
|---|---|---|
| _isi nama_ | _isi NIM_ | _isi peran_ |

## 🤝 Berkontribusi

1. Jalankan `git pull origin main` sebelum mulai bekerja.
2. Buat branch baru: `git checkout -b nama-fitur`
3. Commit perubahan: `git commit -m "Deskripsi singkat"`
4. Push branch: `git push -u origin nama-fitur`
5. Buka **Pull Request** di GitHub untuk ditinjau sebelum digabung ke `main`.

Gunakan struktur folder yang sudah ada (`product/`, `order/`, `payment/`, dst.) saat menambah file baru.

## 📌 Catatan

- `ECommerceSystem.getTotalRevenue()` dan `User.totalSpent` menghitung total harga barang (setelah diskon) **tanpa ongkir**.
- `ShoppingCart` tidak dikosongkan otomatis setelah checkout (`cart.clear()` masih berupa komentar di `User.createOrder()`).
