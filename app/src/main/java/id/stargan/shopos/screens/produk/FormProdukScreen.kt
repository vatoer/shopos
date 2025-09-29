package id.stargan.shopos.screens.produk

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import id.stargan.shopos.viewmodel.ProdukViewModel
import id.stargan.shopos.data.ProdukEntity

@Composable
fun FormProdukScreen(
    navController: NavController,
    produkViewModel: ProdukViewModel,
    produkAwal: ProdukEntity? = null,
    onSimpanProduk: (ProdukEntity) -> Unit
) {
    var sku by remember { mutableStateOf(produkAwal?.sku ?: "") }
    var namaProduk by remember { mutableStateOf(produkAwal?.namaProduk ?: "") }
    var kategoriId by remember { mutableStateOf(produkAwal?.kategoriId ?: 0) }
    var harga by remember { mutableStateOf(produkAwal?.harga?.toString() ?: "") }
    var stok by remember { mutableStateOf(produkAwal?.stok?.toString() ?: "") }
    val daftarKategori = produkViewModel.daftarKategori.value
    var error by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = sku,
            onValueChange = { sku = it },
            label = { Text("SKU") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = namaProduk,
            onValueChange = { namaProduk = it },
            label = { Text("Nama Produk") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (daftarKategori.isEmpty()) {
            Text("Kategori belum tersedia. Tambahkan kategori terlebih dahulu.", color = MaterialTheme.colorScheme.error)
            Button(
                onClick = {
                    // Navigasi ke screen tambah kategori atau tampilkan dialog tambah kategori
                    // Contoh: navController.navigate("tambah_kategori")
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Tambah Kategori")
            }
        } else {
            DropdownMenuKategori(
                daftarKategori = daftarKategori,
                kategoriId = kategoriId,
                onKategoriSelected = { kategoriId = it }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = harga,
            onValueChange = { harga = it },
            label = { Text("Harga") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = stok,
            onValueChange = { stok = it },
            label = { Text("Stok") },
            modifier = Modifier.fillMaxWidth()
        )
        if (error.isNotEmpty()) {
            Text(text = error, color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (sku.isBlank() || namaProduk.isBlank() || harga.isBlank() || stok.isBlank() || kategoriId == 0) {
                error = "Semua field wajib diisi!"
            } else {
                val produk = ProdukEntity(
                    id = produkAwal?.id ?: 0,
                    sku = sku,
                    namaProduk = namaProduk,
                    kategoriId = kategoriId,
                    harga = harga.toDoubleOrNull() ?: 0.0,
                    stok = stok.toIntOrNull() ?: 0
                )
                onSimpanProduk(produk)
                // Hapus navController.popBackStack() agar tetap di halaman produk
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text(if (produkAwal == null) "Tambah Produk" else "Simpan Perubahan")
        }
    }
}

@Composable
fun DropdownMenuKategori(
    daftarKategori: List<id.stargan.shopos.data.KategoriEntity>,
    kategoriId: Int,
    onKategoriSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedKategori = daftarKategori.find { it.id == kategoriId }
    Box {
        OutlinedButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
            Text(selectedKategori?.namaKategori ?: "Pilih Kategori")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            daftarKategori.forEach { kategori ->
                DropdownMenuItem(
                    text = { Text(kategori.namaKategori) },
                    onClick = {
                        onKategoriSelected(kategori.id)
                        expanded = false
                    }
                )
            }
        }
    }
}
