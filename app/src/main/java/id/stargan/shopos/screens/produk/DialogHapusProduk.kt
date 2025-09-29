package id.stargan.shopos.screens.produk

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun DialogHapusProduk(
    namaProduk: String,
    onKonfirmasi: () -> Unit,
    onBatal: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onBatal,
        title = { Text("Hapus Produk") },
        text = { Text("Apakah Anda yakin ingin menghapus produk '$namaProduk'?") },
        confirmButton = {
            Button(onClick = onKonfirmasi) { Text("Hapus") }
        },
        dismissButton = {
            OutlinedButton(onClick = onBatal) { Text("Batal") }
        }
    )
}

