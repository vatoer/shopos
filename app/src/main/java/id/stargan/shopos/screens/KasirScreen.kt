package id.stargan.shopos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import id.stargan.shopos.ui.theme.ShoposTheme
import androidx.compose.foundation.clickable
import id.stargan.shopos.TabItem
import java.text.NumberFormat
import java.util.Locale

// Data models
data class Produk(val idProduk: Int, val namaProduk: String, val hargaProduk: Int)
data class ItemPesanan(val produk: Produk, var jumlah: Int)
enum class StatusOrder { ANTRIAN, SELESAI }
enum class StatusBayar { LUNAS, MENUNGGU }
data class Pesanan(
    val daftarItem: MutableList<ItemPesanan> = mutableListOf(),
    var statusOrder: StatusOrder = StatusOrder.ANTRIAN,
    var statusBayar: StatusBayar = StatusBayar.MENUNGGU
)

object PesananSingleton {
    var pesanan: Pesanan = Pesanan()
}

// PencarianProduk Composable
@Composable
fun PencarianProduk(kataKunci: String, onKataKunciChange: (String) -> Unit) {
    OutlinedTextField(
        value = kataKunci,
        onValueChange = onKataKunciChange,
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            if (kataKunci.isNotEmpty()) {
                IconButton(onClick = { onKataKunciChange("") }) {
                    Icon(Icons.Default.Close, contentDescription = "Clear")
                }
            }
        },
        label = { Text("Cari Produk") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth().padding(4.dp)
    )
}

// ProdukList Composable
@Composable
fun ProdukList(
    daftarProduk: List<Produk>,
    jumlahMap: Map<Int, Int>,
    onTambah: (Produk) -> Unit,
    onKurang: (Produk) -> Unit
) {
    Column {
        if (daftarProduk.isEmpty()) {
            Text("Tidak ada produk ditemukan", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(8.dp))
        } else {
            LazyColumn(
                Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(0.dp)
            ) {
                items(daftarProduk.size) { idx ->
                    val produk = daftarProduk[idx]
                    val jumlah = jumlahMap[produk.idProduk] ?: 0
                    val totalPerProduk = jumlah * produk.hargaProduk
                    val isOdd = idx % 2 == 1
                    val rowBg = if (isOdd) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(rowBg)
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(produk.namaProduk, fontWeight = FontWeight.Medium, fontSize = 20.sp)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Rp${formatRupiah(produk.hargaProduk)}", fontSize = 14.sp)
                                if (jumlah > 0) {
                                    Spacer(Modifier.width(8.dp))
                                    Text("x $jumlah = Rp${formatRupiah(totalPerProduk)}", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { onKurang(produk) }, enabled = jumlah > 0) {
                                Icon(Icons.Default.Delete, contentDescription = "Kurangi")
                            }
                            Text("$jumlah", Modifier.width(32.dp), textAlign = TextAlign.Center)
                            IconButton(onClick = { onTambah(produk) }) {
                                Icon(Icons.Default.Add, contentDescription = "Tambah")
                            }
                        }
                    }
                    if (idx < daftarProduk.lastIndex) {
                        HorizontalDivider(
                            thickness = 0.5.dp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                        )
                    }
                }
            }
        }
    }
}

// RingkasanActions Composable
@Composable
fun RingkasanActions(
    pesanan: Pesanan,
    onSimpan: () -> Unit,
    onBayar: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = onSimpan,
            enabled = pesanan.statusOrder != StatusOrder.SELESAI && pesanan.statusBayar != StatusBayar.LUNAS,
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Default.Check, contentDescription = null)
            Spacer(Modifier.width(4.dp))
            Text("Simpan")
        }
        Button(
            onClick = onBayar,
            enabled = pesanan.daftarItem.isNotEmpty() && pesanan.statusBayar != StatusBayar.LUNAS,
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Default.Done, contentDescription = null)
            Spacer(Modifier.width(4.dp))
            Text("Bayar")
        }
    }
}

// InfoSection Composable
@Composable
fun InfoSection(
    jumlahProduk: Int,
    jumlahItem: Int,
    totalHarga: Int,
    onLihatSemua: () -> Unit,
    showOnlySelected: Boolean = false
) {
    Column(Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 8.dp)) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("$jumlahProduk produk, $jumlahItem item", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
            Spacer(Modifier.weight(1f))
            TextButton(onClick = onLihatSemua) {
                Text(if (showOnlySelected) "lihat semua" else "lihat pesanan", style = MaterialTheme.typography.bodyMedium)
            }
        }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                "Total: ${formatRupiah(totalHarga)}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp
            )
        }
    }
}

// Main KasirScreen Composable
@Composable
fun KasirScreen(
    navController: androidx.navigation.NavHostController? = null,
    pesananId: Int = -1 // -1 for new, >=0 for editing
) {
    val daftarProduk = remember {
        mutableStateOf(
            listOf(
                Produk(1, "Kopi Hitam", 15000),
                Produk(2, "Kopi Susu", 20000),
                Produk(3, "Teh Tarik", 18000),
                Produk(4, "Roti", 10000),
                Produk(5, "Air Mineral", 8000),
                Produk(6, "Nasi Goreng", 25000),
                Produk(7, "Mie Ayam", 22000),
                Produk(8, "Es Teh", 7000),
                Produk(9, "Es Jeruk", 9000),
                Produk(10, "Kue Lapis", 12000),
                Produk(11, "Kue Cubit", 15000),
                Produk(12, "Kue Putu", 13000),
                Produk(13, "Kue Pancong", 14000),
                Produk(14, "Kue Apem", 11000),
                Produk(15, "Kue Serabi", 16000),
                Produk(16, "Kue Lumpur", 17000),
                Produk(17, "Kue Talam", 18000),
                Produk(18, "Kue Bugis", 19000),
                Produk(19, "Kue Dadar Gulung", 20000),
                Produk(20, "Kue Klepon", 21000),
                Produk(21, "Kue Onde-onde", 22000),
                Produk(22, "Kue Rangi", 23000),
                Produk(23, "Kue Bika Ambon", 24000),
                Produk(24, "Kue Lapis Legit", 25000),
                Produk(25, "Kue Nastar", 26000),
                Produk(26, "Kue Kastengel", 27000),
                Produk(27, "Kue Putri Salju", 28000),
                Produk(28, "Kue Semprit", 29000),
                Produk(29, "Kue Sagu", 30000),
                Produk(30, "Kue Kering", 31000),
                Produk(31, "Kue Basah", 32000)
            )
        )
    }
    val kataKunci = remember { mutableStateOf("") }
    val pesanan = remember { mutableStateOf(Pesanan()) }
    val showDialogBayar = remember { mutableStateOf(false) }
    val showDialogSimpan = remember { mutableStateOf(false) }
    val showOnlySelected = remember { mutableStateOf(false) }

    val produkTersaring = if (kataKunci.value.isBlank()) daftarProduk.value else daftarProduk.value.filter {
        it.namaProduk.contains(kataKunci.value, ignoreCase = true)
    }
    val jumlahMap = pesanan.value.daftarItem.groupBy({ it.produk.idProduk }, { it.jumlah }).mapValues { it.value.sum() }
    val cartItems = daftarProduk.value.mapNotNull { produk ->
        val jumlah = jumlahMap[produk.idProduk] ?: 0
        if (jumlah > 0) ItemPesanan(produk, jumlah) else null
    }
    val jumlahProduk = cartItems.size
    val jumlahItem = cartItems.sumOf { it.jumlah }
    val totalHarga = cartItems.sumOf { it.jumlah * it.produk.hargaProduk }

    val produkListToShow = if (showOnlySelected.value) {
        daftarProduk.value.filter { jumlahMap[it.idProduk] ?: 0 > 0 }
    } else {
        produkTersaring
    }

    Scaffold(
        bottomBar = {
            Column(
                Modifier.fillMaxWidth()
            ) {
                InfoSection(
                    jumlahProduk = jumlahProduk,
                    jumlahItem = jumlahItem,
                    totalHarga = totalHarga,
                    onLihatSemua = {
                        showOnlySelected.value = !showOnlySelected.value
                    },
                    showOnlySelected = showOnlySelected.value
                )
                RingkasanActions(
                    pesanan = pesanan.value,
                    onSimpan = { showDialogSimpan.value = true },
                    onBayar = { showDialogBayar.value = true }
                )
            }
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text("Kasir", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp, start = 4.dp))
            PencarianProduk(kataKunci.value) { kataKunci.value = it }
            Spacer(Modifier.height(8.dp))
            ProdukList(
                produkListToShow,
                jumlahMap,
                onTambah = { produk -> pesanan.value = tambahItemKePesanan(pesanan.value, produk) },
                onKurang = { produk -> pesanan.value = kurangiItemDariPesanan(pesanan.value, produk) }
            )
        }
    }
    if (showDialogBayar.value) {
        AlertDialog(
            onDismissRequest = { showDialogBayar.value = false },
            title = { Text("Konfirmasi Pembayaran") },
            text = { Text("Apakah Anda yakin ingin membayar pesanan ini?") },
            confirmButton = {
                Button(onClick = { bayarPesanan(pesanan.value); showDialogBayar.value = false }) {
                    Text("Bayar")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDialogBayar.value = false }) {
                    Text("Batal")
                }
            }
        )
    }
    if (showDialogSimpan.value) {
        AlertDialog(
            onDismissRequest = { showDialogSimpan.value = false },
            title = { Text("Konfirmasi Simpan Pesanan") },
            text = { Text("Simpan pesanan ke antrian?") },
            confirmButton = {
                Button(onClick = { simpanPesanan(pesanan.value); showDialogSimpan.value = false }) {
                    Text("Simpan")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDialogSimpan.value = false }) {
                    Text("Batal")
                }
            }
        )
    }
}

// Helper functions
fun tambahItemKePesanan(pesanan: Pesanan, produk: Produk): Pesanan {
    val daftarBaru = pesanan.daftarItem.map { it.copy() }.toMutableList()
    val item = daftarBaru.find { it.produk.idProduk == produk.idProduk }
    if (item != null) {
        item.jumlah++
    } else {
        daftarBaru.add(ItemPesanan(produk, 1))
    }
    return pesanan.copy(daftarItem = daftarBaru)
}

fun kurangiItemDariPesanan(pesanan: Pesanan, produk: Produk): Pesanan {
    val daftarBaru = pesanan.daftarItem.map { it.copy() }.toMutableList()
    val item = daftarBaru.find { it.produk.idProduk == produk.idProduk }
    if (item != null) {
        item.jumlah--
        if (item.jumlah <= 0) daftarBaru.remove(item)
    }
    return pesanan.copy(daftarItem = daftarBaru)
}

fun simpanPesanan(pesanan: Pesanan) {
    pesanan.statusOrder = StatusOrder.ANTRIAN
}

fun bayarPesanan(pesanan: Pesanan) {
    pesanan.statusBayar = StatusBayar.LUNAS
}

fun formatRupiah(number: Int): String {
    val format = NumberFormat.getNumberInstance(Locale("in", "ID"))
    return format.format(number)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ShoposTheme {
        // Sample data
        val sampleProduk = listOf(
            Produk(1, "Kopi Hitam", 15000),
            Produk(2, "Kopi Susu", 20000),
            Produk(3, "Teh Tarik", 18000),
            Produk(4, "Roti", 10000),
            Produk(5, "Air Mineral", 8000),
            Produk(6, "Nasi Goreng", 25000),
            Produk(7, "Mie Ayam", 22000),
            Produk(8, "Es Teh", 7000),
            Produk(9, "Es Jeruk", 9000),
            Produk(10, "Kue Lapis", 12000),
            Produk(11, "Kue Cubit", 15000),
            Produk(12, "Kue Putu", 13000),
            Produk(13, "Kue Pancong", 14000),
            Produk(14, "Kue Apem", 11000),
            Produk(15, "Kue Serabi", 16000),
            Produk(16, "Kue Lumpur", 17000),
            Produk(17, "Kue Talam", 18000),
            Produk(18, "Kue Bugis", 19000),
            Produk(19, "Kue Dadar Gulung", 20000),
            Produk(20, "Kue Klepon", 21000),
            Produk(21, "Kue Onde-onde", 22000),
            Produk(22, "Kue Rangi", 23000),
            Produk(23, "Kue Bika Ambon", 24000),
            Produk(24, "Kue Lapis Legit", 25000),
            Produk(25, "Kue Nastar", 26000),
            Produk(26, "Kue Kastengel", 27000),
            Produk(27, "Kue Putri Salju", 28000),
            Produk(28, "Kue Semprit", 29000),
            Produk(29, "Kue Sagu", 30000),
            Produk(30, "Kue Kering", 31000),
            Produk(31, "Kue Basah", 32000)
        )

        // State for search keyword
        val searchKeyword = remember { mutableStateOf("") }

        // Filtered product list based on search keyword
        val filteredProduk = if (searchKeyword.value.isBlank()) {
            sampleProduk
        } else {
            sampleProduk.filter { it.namaProduk.contains(searchKeyword.value, ignoreCase = true) }
        }

        Column(Modifier.padding(16.dp)) {
            PencarianProduk(searchKeyword.value) { newKeyword ->
                searchKeyword.value = newKeyword
            }
            Spacer(modifier = Modifier.height(16.dp))
            ProdukList(
                filteredProduk,
                jumlahMap = mapOf(), // pass empty map for preview
                onTambah = { /* TODO: Handle product click */ },
                onKurang = { /* TODO: Handle product click */ }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPencarianProduk() {
    PencarianProduk(kataKunci = "Kopi", onKataKunciChange = {})
}

@Preview(showBackground = true)
@Composable
fun PreviewProdukList() {
    ProdukList(
        daftarProduk = listOf(
            Produk(1, "Kopi Hitam", 15000),
            Produk(2, "Kopi Susu", 20000),
            Produk(3, "Teh Tarik", 18000)
        ),
        jumlahMap = mapOf(),
        onTambah = {},
        onKurang = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewRingkasanActions() {
    RingkasanActions(
        pesanan = Pesanan(
            daftarItem = mutableListOf(
                ItemPesanan(Produk(1, "Kopi Hitam", 15000), 2)
            )
        ),
        onSimpan = {},
        onBayar = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewInfoSection() {
    InfoSection(
        jumlahProduk = 2,
        jumlahItem = 3,
        totalHarga = 50000,
        onLihatSemua = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewKasirScreen() {
    KasirScreen()
}
