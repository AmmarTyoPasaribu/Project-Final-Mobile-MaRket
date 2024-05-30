## MaRket , Aplikasi Mobile E_Commerce.

Aplikasi ini menggunakan API dari website https://dummyjson.com/products , yang berisikan produk produk yang dijual di aplikasi MaRket


Dokumentasi Aplikasi
Deskripsi Aplikasi
Aplikasi ini adalah sebuah platform belanja sederhana yang memungkinkan pengguna untuk mendaftar, login, melihat profil, menambah produk ke keranjang belanja, menghapus produk dari keranjang, dan melanjutkan ke proses pembayaran. Aplikasi ini juga menyediakan fitur untuk mengedit profil pengguna, termasuk mengubah nama pengguna dan kata sandi.


Cara Penggunaan

Register:
Masukkan nama pengguna (maksimal 15 karakter) dan kata sandi (maksimal 8 karakter).
Jika nama pengguna atau kata sandi melebihi batas karakter yang ditentukan, akan muncul pesan kesalahan.
Setelah berhasil mendaftar, pengguna akan otomatis memiliki saldo awal sebesar 1000.

Login:
Setelah mendaftar, kembali ke halaman login.
Masukkan nama pengguna dan kata sandi yang telah didaftarkan.
Setelah berhasil login, pengguna akan diarahkan ke halaman utama aplikasi.

Melihat dan Mengedit Profil:
Di halaman utama, pengguna dapat memilih opsi untuk melihat atau mengedit profil.
Pengguna dapat mengganti nama pengguna, kata sandi, dan foto profil.
Jika nama pengguna atau kata sandi melebihi batas karakter yang ditentukan saat diubah, akan muncul pesan kesalahan.

Menambah Produk ke Keranjang:
Di halaman utama, pilih produk yang ingin dibeli dan tambahkan ke keranjang.
Pengguna dapat melihat produk yang telah ditambahkan ke keranjang.

Menghapus Produk dari Keranjang:
Di halaman keranjang, pengguna dapat memilih produk yang ingin dihapus.
Sebuah dialog konfirmasi akan muncul untuk memastikan penghapusan produk dari keranjang.

Melanjutkan ke Pembayaran:
Di halaman keranjang, pengguna dapat melanjutkan ke proses pembayaran jika terdapat produk di keranjang.
Jika keranjang kosong, akan muncul pesan "Belanja dulu baru bisa bayar".


Penjelasan Singkat Tentang Implementasi Teknis

Struktur Proyek
Proyek ini terdiri dari beberapa aktivitas utama:

RegisterActivity - Aktivitas untuk mendaftar pengguna baru.
LoginActivity - Aktivitas untuk login pengguna.
ProfileActivity - Aktivitas untuk melihat dan mengedit profil pengguna.
MainActivity - Aktivitas utama yang menampilkan produk-produk yang tersedia.
CartActivity - Aktivitas yang menampilkan keranjang belanja pengguna.
PaymentActivity - Aktivitas untuk proses pembayaran.
RegisterActivity
Deskripsi: Aktivitas ini memungkinkan pengguna baru untuk mendaftar. Nama pengguna dan kata sandi disimpan dalam SharedPreferences.
Fitur Utama:
Validasi panjang nama pengguna dan kata sandi.
Penyimpanan data pengguna dalam SharedPreferences.

ProfileActivity
Deskripsi: Aktivitas ini memungkinkan pengguna untuk melihat dan mengedit profil mereka.
Fitur Utama:
Mengambil dan menampilkan data profil dari SharedPreferences.
Validasi panjang nama pengguna dan kata sandi saat mengedit profil.
Menyimpan perubahan profil ke SharedPreferences.

CartActivity
Deskripsi: Aktivitas ini memungkinkan pengguna untuk melihat dan mengelola produk di keranjang belanja mereka.
Fitur Utama:
Menampilkan produk yang ditambahkan ke keranjang.
Menghapus produk dari keranjang dengan konfirmasi.
Melanjutkan ke pembayaran jika ada produk di keranjang.

PaymentActivity
Deskripsi: Aktivitas ini memungkinkan pengguna untuk melanjutkan proses pembayaran.
Fitur Utama:
Melakukan pengecekan keranjang belanja.
Menampilkan pesan jika keranjang kosong.
