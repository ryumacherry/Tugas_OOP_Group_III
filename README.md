# Sistem Manajemen E-Commerce

Proyek ini merupakan simulasi sistem *e-commerce* berbasis konsol (*command-line*) yang dibangun menggunakan bahasa Kotlin. Proyek ini dibuat untuk tugas kuliah Pemrograman Berorientasi Objek (PBO) guna mempraktikkan langsung konsep-konsep utama seperti pewarisan, polimorfisme, abstraksi, enkapsulasi, antarmuka (*interface*), dan *sealed class*.

Di sini pengguna bisa melihat katalog produk, mendaftarkan akun, mengelola keranjang belanja, melakukan proses *checkout* dengan berbagai metode pembayaran, serta memilih kurir pengiriman berdasarkan jarak.

---

## Gambaran Fitur

- **Pengelolaan Produk,** Menambahkan barang, menampilkan seluruh katalog, dan mencari produk berdasarkan nama atau ID.
- **Kategori Produk & Diskon,** Tersedia tiga jenis produk (Elektronik, Pakaian, Makanan) dengan logika perhitungan diskon masing-masing.
- **Pengelolaan Pengguna,** Registrasi akun baru, validasi kata sandi, serta pencatatan riwayat belanja tiap pengguna.
- **Keranjang Belanja,** Menambah atau menghapus barang dengan pengecekan stok otomatis.
- **Metode Pembayaran,** Simulasi pembayaran lewat Kartu Kredit, Transfer Bank, dan QRIS lengkap dengan biaya admin masing-masing.
- **Status Pesanan Terstruktur,** Menggunakan *sealed class* untuk mengunci status pesanan (*Pending*, *Paid*, *Shipped*, *Delivered*, *Cancelled*).
- **Simulasi Pengiriman,** Menghitung ongkos kirim dan estimasi waktu berdasarkan jarak tempuh (Instant dan Same Day).
- **Laporan Penjualan,** Rekapitulasi jumlah transaksi dan total pendapatan toko.

---

## Logika Bisnis

### 1. Skema Pengiriman Berdasarkan Jarak
Perhitungan ongkos kirim membulatkan jarak ke atas (misalnya 8,2 km dihitung 9 km). Jika jarak pesanan melebihi jangkauan layanan, sistem otomatis membatalkan proses *checkout*. Pesanan dengan jarak lebih dari 40 km tidak dapat dilayani.

- **Instant (0–15 km),** Tarifnya Rp 10.000 + Rp 2.500/km (Estimasi 1–3 jam).
- **Same Day (0–40 km),** Tarifnya Rp 8.000 + Rp 1.500/km (Estimasi 6–12 jam).

### 2. Aturan Diskon Produk
- **Barang Elektronik,** Diskon dasar 5%. Mendapat tambahan 10% jika tergolong produk premium, dan tambahan 5% jika masa garansi lebih dari 24 bulan (maksimal 20%).
- **Pakaian,** Diskon dasar 10%. Mendapat tambahan 15% untuk koleksi musiman (*seasonal*), dan tambahan 5% untuk ukuran besar (XL, XXL, XXXL).
- **Makanan,** Diskon dasar 5%. Mendapat tambahan 20% jika merupakan produk organik.

### 3. Biaya Layanan Pembayaran
- **Kartu Kredit,** Biaya 2% dari total belanja (validasi nomor kartu minimal 16 digit dan CVV 3 digit).
- **Transfer Bank,** Biaya 1% dengan tarif minimal Rp 5.000 (validasi nomor rekening minimal 8 digit).
- **QRIS,** Biaya 0,5% (validasi kode QR minimal 10 karakter).

---

## Penerapan Konsep PBO

- **Abstraction,** Diterapkan pada `Product` sebagai *abstract class* yang mewajibkan kelas turunannya mendefinisikan implementasi `calculateDiscount()` dan `getCategory()`.
- **Inheritance,** Kelas `ElectronicProduct`, `ClothingProduct`, dan `FoodProduct` mewarisi properti serta fungsi dasar dari `Product`.
- **Polymorphism,** Perhitungan harga akhir dan diskon berjalan dinamis tergantung objek aslinya, begitu juga dengan implementasi beragam metode pembayaran dan pengiriman.
- **Encapsulation,** Properti sensitif seperti `price` pada produk dan `password` pada pengguna diset `private` dan hanya bisa diakses atau divalidasi lewat fungsi perantara.
- **Interface,** Digunakan pada `PaymentMethod` dan `ShippingMethod` sebagai kontrak modular untuk mempermudah penambahan opsi baru di masa mendatang.
- **Sealed Class,** Digunakan pada `OrderStatus` dan `PaymentResult` agar seluruh kemungkinan status terdefinisi dengan pasti (*exhaustive checking*) saat dievaluasi menggunakan ekspresi `when`.

---

## Struktur Folder

```text
src/
├── Main.kt                     # Titik masuk program untuk menjalankan demo
├── cart/
│   └── ShoppingCart.kt         # Logika keranjang belanja
├── order/
│   ├── Order.kt                # Entitas pesanan
│   └── OrderStatus.kt          # Definisi sealed class status pesanan
├── payment/
│   ├── PaymentMethod.kt        # Interface pembayaran & sealed class PaymentResult
│   ├── CreditCardPayment.kt    # Pembayaran via kartu kredit
│   ├── BankTransferPayment.kt  # Pembayaran via transfer bank
│   └── QRISPayment.kt          # Pembayaran via QRIS
├── product/
│   ├── Product.kt              # Kelas induk abstrak produk
│   ├── ElectronicProduct.kt    # Kategori produk elektronik
│   ├── ClothingProduct.kt      # Kategori produk pakaian
│   └── FoodProduct.kt          # Kategori produk makanan
├── shipping/
│   ├── ShippingMethod.kt       # Interface pengiriman & penampung opsi
│   ├── InstantShipping.kt      # Layanan instan
│   └── SameDayShipping.kt      # Layanan same day
├── system/
│   └── EcommerceSystem.kt      # Pengendali utama sistem toko
└── user/
    └── User.kt                 # Entitas pengguna dan proses checkout
```

---

## Cara Menjalankan

### Menggunakan IntelliJ IDEA
1. *Clone* repositori ini ke komputer:
   ```bash
   git clone [https://github.com/ryumacherry/Tugas_OOP_Group_III.git](https://github.com/ryumacherry/Tugas_OOP_Group_III.git)
   ```
2. Buka folder proyek melalui menu **File > Open** di IntelliJ IDEA.
3. Pastikan folder `src` sudah ditandai sebagai direktori sumber (*Sources Root*).
4. Buka berkas `src/Main.kt`, lalu jalankan program dengan menekan tombol **Run** (ikon segitiga hijau) di samping fungsi `main()`.

### Menggunakan Terminal / Command Line
Pastikan JDK (versi 17 atau lebih baru) dan `kotlinc` sudah terpasang di sistem.

```bash
# Kompilasi seluruh file Kotlin menjadi file jar
kotlinc $(find src -name '*.kt') -include-runtime -d ecommerce.jar

# Jalankan aplikasi
java -jar ecommerce.jar
```

---

## Anggota Tim

| Nama | NIM | Peran |
|---|---|---|
| Ajeng Az Zahra | 225443002 | Backend Developer 2 |
| Arlisya Rahwitami | 225443004 | Backend Developer 1 |
| Nabila Khalishah | 225443021 | Documentation Lead |
| Qurrata A'yuni | 225443023 | Project Lead |
| Renisa Rahmawati | 225443025 | System Developer |
| Shiva Nayla Zahra | 225443053 | Payment Developer |

---

## Catatan Teknis

- Perhitungan pada `ECommerceSystem.getTotalRevenue()` dan pengeluaran di `User.totalSpent` menghitung total harga produk setelah diskon tanpa menjumlahkan ongkos kirim.
- Pembersihan keranjang secara otomatis setelah *checkout* masih dinonaktifkan (`cart.clear()` di `User.createOrder()` masih berupa komentar) agar data referensi keranjang tetap bisa dibaca saat demonstrasi.
