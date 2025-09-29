package id.stargan.shopos.screens.pengaturan

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import id.stargan.shopos.data.KategoriEntity
import id.stargan.shopos.data.KategoriRepository
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Alignment

@Composable
fun KategoriScreen(repository: KategoriRepository) {
    val kategoriList by repository.ambilSemuaKategori().collectAsState(initial = emptyList())
    var namaKategori by remember { mutableStateOf(TextFieldValue("")) }
    var editId by remember { mutableStateOf<Int?>(null) }
    val scope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Pengaturan Kategori Produk", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = namaKategori,
            onValueChange = { namaKategori = it },
            label = { Text("Nama Kategori") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Row {
            Button(
                onClick = {
                    scope.launch {
                        if (editId == null) {
                            repository.tambahKategori(KategoriEntity(namaKategori = namaKategori.text.trim()))
                        } else {
                            repository.ubahKategori(KategoriEntity(id = editId!!, namaKategori = namaKategori.text.trim()))
                            editId = null
                        }
                        namaKategori = TextFieldValue("")
                    }
                },
                enabled = namaKategori.text.isNotBlank(),
                modifier = Modifier.weight(1f)
            ) {
                Text(if (editId == null) "Tambah" else "Simpan")
            }
            if (editId != null) {
                Spacer(Modifier.width(8.dp))
                OutlinedButton(
                    onClick = {
                        editId = null
                        namaKategori = TextFieldValue("")
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Batal")
                }
            }
        }
        Spacer(Modifier.height(24.dp))
        Text("Daftar Kategori", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        // Tabular view
        Row(
            Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("No", Modifier.width(40.dp), textAlign = TextAlign.Center, style = MaterialTheme.typography.labelMedium)
            Text("Nama", Modifier.weight(2f), style = MaterialTheme.typography.labelMedium)
            Text("Aksi", Modifier.width(100.dp), textAlign = TextAlign.Center, style = MaterialTheme.typography.labelMedium)
        }
        Divider()
        LazyColumn {
            items(kategoriList) { kategori ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text((kategoriList.indexOf(kategori) + 1).toString(), Modifier.width(40.dp), textAlign = TextAlign.Center)
                    Text(kategori.namaKategori, Modifier.weight(2f), style = MaterialTheme.typography.bodyLarge)
                    Row(Modifier.width(100.dp), horizontalArrangement = Arrangement.Center) {
                        IconButton(onClick = {
                            editId = kategori.id
                            namaKategori = TextFieldValue(kategori.namaKategori)
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = {
                            scope.launch { repository.hapusKategori(kategori) }
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Hapus")
                        }
                    }
                }
                Divider()
            }
        }
    }
}
