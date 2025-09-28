build modular kasir UI base on best practice UI/UX

- pencarian -> akan memfilter dari produk
- daftar produk -> di load dari database lokal -> bisa di klik
- produk yang di klik akan di tambahkan ke item Pesanan
- Ringkasan Pesanan terdiri atas
  -- 3 produk terakhir yang di tambahkan dengan tombol plus minus untuk dapat menambah dan mengurangu jumlah item
  -- jika sampe 0 , item di hapus dari keranjang
  -- setiap row item yang di order berisi dengan nama produk ( n x harga = total )
  -- mempunyai mekanisme untuk melihat semua daftar item yang dipesan
  -- mempunyai keterangan berapa produk yang di order dan jumlah item ( 5 produk, 8 item) kemudian harga total
- dapat menyimpan pesanan dan item pesanan , ketika disimpan akan ada status order -> antrian | selesai <penyederhanaan status>
- mempunya status bayar : lunas | menunggu pembayaran
- status order dan status bayar independent tidak saling mempengaruhi
- pesanan/order hanya bisa diubah jika
  -- status order != selesai atau status bayar = lunas
  -- pesanan yang sudah lunas tidak dapat diubah ( harus bikin pesanan baru )
  -- pesanan yang sudah selesai tidak bisa diubah ( jika mau menambah item baru maka sama dengan bikin order baru )
- mempunyai tombol simpan dan bayar
  -- tombol simpan disini untuk membuat status order -> antrian
  -- tombol bayar disini untuk membuat status bayar

buat variabel-variabel dalam bahasa Indonesia
fungsi juga dalam bahasa indonesia 
 

