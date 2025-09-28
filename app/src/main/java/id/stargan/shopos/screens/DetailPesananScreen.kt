package id.stargan.shopos.screens

import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPesananScreen(
    navController: NavController,
    pesanan: Pesanan
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detail Pesanan") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.List, contentDescription = "Kembali ke Kasir")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding).padding(16.dp)) {
            Text("Semua Item Dipesan", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            pesanan.daftarItem.forEach { item ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(item.produk.namaProduk)
                    Text("${item.jumlah} x Rp${item.produk.hargaProduk} = Rp${item.jumlah * item.produk.hargaProduk}")
                }
                Divider()
            }
            Spacer(Modifier.height(16.dp))
            val jumlahProduk = pesanan.daftarItem.size
            val jumlahItem = pesanan.daftarItem.sumOf { it.jumlah }
            val totalHarga = pesanan.daftarItem.sumOf { it.jumlah * it.produk.hargaProduk }
            Text("${jumlahProduk} produk, ${jumlahItem} item", style = MaterialTheme.typography.bodyMedium)
            Text("Total: Rp${totalHarga}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
        }
    }
}
