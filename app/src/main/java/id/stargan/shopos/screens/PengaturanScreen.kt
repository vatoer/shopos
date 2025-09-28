package id.stargan.shopos.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable // Ensured this import is present
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.background
import id.stargan.shopos.screens.pengaturan.KategoriScreen
import id.stargan.shopos.data.repository.KategoriProdukRepository
import androidx.compose.ui.platform.LocalContext
import id.stargan.shopos.data.db.AppDatabase

sealed class PengaturanMenu(val title: String) {
    object ProfilToko : PengaturanMenu("Profil Toko")
    object Pengguna : PengaturanMenu("Pengguna")
    object KategoriProduk : PengaturanMenu("Kategori Produk")
    object Diskon : PengaturanMenu("Diskon")
    object Promo : PengaturanMenu("Promo")
    object Printer : PengaturanMenu("Printer")
}

@Composable
fun PengaturanScreen(
    navController: NavController,
    showTabBar: (Boolean) -> Unit
) {
    var selectedMenu by remember { mutableStateOf<PengaturanMenu?>(null) }
    LaunchedEffect(selectedMenu) {
        showTabBar(selectedMenu == null)
    }
    if (selectedMenu == null) {
        // Main settings list
        Column(Modifier.fillMaxSize()) {
            Text("Pengaturan", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp))
            PengaturanList(onMenuClick = { selectedMenu = it })
        }
    } else {
        // Sub-page
        PengaturanSubScreen(menu = selectedMenu!!, onBack = { selectedMenu = null })
    }
}

@Composable
fun PengaturanList(onMenuClick: (PengaturanMenu) -> Unit) {
    val menuItems = listOf(
        PengaturanMenu.ProfilToko,
        PengaturanMenu.Pengguna,
        PengaturanMenu.KategoriProduk,
        PengaturanMenu.Diskon,
        PengaturanMenu.Promo,
        PengaturanMenu.Printer
    )
    Column(Modifier.fillMaxWidth()) {
        menuItems.forEachIndexed { idx, menu ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable { onMenuClick(menu) }
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(menu.title, Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
                Icon(Icons.Default.ChevronRight, contentDescription = null)
            }
            if (idx < menuItems.lastIndex) {
                HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
            }
        }
    }
}

@Composable
fun PengaturanSubScreen(menu: PengaturanMenu, onBack: () -> Unit) {
    // Use stable TopAppBar and Scaffold APIs
    Column(Modifier.fillMaxSize()) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(MaterialTheme.colorScheme.primaryContainer),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
            }
            Text(
                menu.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Divider()
        Box(Modifier.fillMaxSize()) {
            when (menu) {
                PengaturanMenu.ProfilToko -> ProfilTokoSetting()
                PengaturanMenu.Pengguna -> PenggunaSetting()
                PengaturanMenu.KategoriProduk -> {
                    val context = LocalContext.current
                    val kategoriDao = remember { AppDatabase.getDatabase(context).kategoriProdukDao() }
                    val kategoriRepo = remember { KategoriProdukRepository(kategoriDao) }
                    KategoriScreen(repository = kategoriRepo)
                }
                PengaturanMenu.Diskon -> DiskonSetting()
                PengaturanMenu.Promo -> PromoSetting()
                PengaturanMenu.Printer -> PrinterSetting()
            }
        }
    }
}

// Modular composables for each setting
@Composable
fun ProfilTokoSetting() {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Pengaturan Profil Toko", style = MaterialTheme.typography.titleMedium)
        // ...implement form/profile info here...
    }
}

@Composable
fun PenggunaSetting() {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Pengaturan Pengguna", style = MaterialTheme.typography.titleMedium)
        // ...implement user management here...
    }
}

@Composable
fun DiskonSetting() {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Pengaturan Diskon", style = MaterialTheme.typography.titleMedium)
        // ...implement discount management here...
    }
}

@Composable
fun PromoSetting() {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Pengaturan Promo", style = MaterialTheme.typography.titleMedium)
        // ...implement promo management here...
    }
}

@Composable
fun PrinterSetting() {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Pengaturan Printer", style = MaterialTheme.typography.titleMedium)
        // ...implement printer settings here...
    }
}
