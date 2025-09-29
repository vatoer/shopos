package id.stargan.shopos.screens.produk

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import id.stargan.shopos.viewmodel.ProdukViewModel
import id.stargan.shopos.data.ProdukEntity

@Composable
fun ProdukScreen(
    navController: NavController,
    produkViewModel: ProdukViewModel
) {
    var tampilForm by remember { mutableStateOf(false) }
    var produkDipilih by remember { mutableStateOf<ProdukEntity?>(null) }
    var tampilDialogHapus by remember { mutableStateOf(false) }

    // Daftar produk
    DaftarProdukScreen(
        navController = navController,
        produkViewModel = produkViewModel,
        onTambahProduk = {
            produkDipilih = null
            tampilForm = true
        },
        onUbahProduk = { id ->
            produkDipilih = produkViewModel.daftarProduk.value.find { it.id == id }
            tampilForm = true
        },
        onHapusProduk = { id ->
            produkDipilih = produkViewModel.daftarProduk.value.find { it.id == id }
            tampilDialogHapus = true
        }
    )

    // Form tambah/ubah produk
    if (tampilForm) {
        AlertDialog(
            onDismissRequest = { tampilForm = false },
            title = { Text(if (produkDipilih == null) "Tambah Produk" else "Ubah Produk") },
            text = {
                FormProdukScreen(
                    navController = navController,
                    produkViewModel = produkViewModel,
                    produkAwal = produkDipilih,
                    onSimpanProduk = { produk ->
                        if (produkDipilih == null) {
                            produkViewModel.tambahProduk(produk)
                        } else {
                            produkViewModel.ubahProduk(produk)
                        }
                        tampilForm = false
                    }
                )
            },
            confirmButton = {},
            dismissButton = {}
        )
    }

    // Dialog hapus produk
    if (tampilDialogHapus && produkDipilih != null) {
        DialogHapusProduk(
            namaProduk = produkDipilih!!.namaProduk,
            onKonfirmasi = {
                produkViewModel.hapusProduk(produkDipilih!!)
                tampilDialogHapus = false
            },
            onBatal = { tampilDialogHapus = false }
        )
    }
}
