package id.stargan.shopos.screens.produk

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import id.stargan.shopos.viewmodel.ProdukViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun DaftarProdukScreen(
    navController: NavController,
    produkViewModel: ProdukViewModel,
    onTambahProduk: () -> Unit,
    onUbahProduk: (id: Int) -> Unit,
    onHapusProduk: (id: Int) -> Unit
) {
    val daftarProduk by produkViewModel.daftarProduk.collectAsState()
    Scaffold(
        topBar = {
            Surface(shadowElevation = 4.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Text(
                        text = "Daftar Produk",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onTambahProduk) {
                Text("+")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(daftarProduk.size) { index ->
                val produk = daftarProduk[index]
                ItemProduk(
                    produk = produk,
                    onUbahProduk = { onUbahProduk(produk.id) },
                    onHapusProduk = { onHapusProduk(produk.id) }
                )
            }
        }
    }
}
