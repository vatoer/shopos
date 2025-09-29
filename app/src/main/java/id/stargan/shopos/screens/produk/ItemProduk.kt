package id.stargan.shopos.screens.produk

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.stargan.shopos.data.ProdukEntity

@Composable
fun ItemProduk(
    produk: ProdukEntity,
    onUbahProduk: () -> Unit,
    onHapusProduk: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = produk.namaProduk, style = MaterialTheme.typography.titleMedium)
            Text(text = "SKU: ${produk.sku}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Harga: Rp${produk.harga}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Stok: ${produk.stok}", style = MaterialTheme.typography.bodySmall)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onUbahProduk) { Text("Ubah") }
                TextButton(onClick = onHapusProduk) { Text("Hapus") }
            }
        }
    }
}

