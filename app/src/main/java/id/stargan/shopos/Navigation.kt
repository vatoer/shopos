package id.stargan.shopos

import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.stargan.shopos.screens.KasirScreen
import id.stargan.shopos.screens.LaporanScreen
import id.stargan.shopos.screens.PengaturanScreen
import id.stargan.shopos.screens.ProdukScreen
import id.stargan.shopos.screens.PesananScreen
import id.stargan.shopos.screens.DetailPesananScreen
import id.stargan.shopos.screens.PesananSingleton
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import id.stargan.shopos.data.db.DatabaseProvider
import id.stargan.shopos.data.ProdukRepository
import id.stargan.shopos.data.KategoriRepository
import id.stargan.shopos.viewmodel.ProdukViewModel
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import id.stargan.shopos.viewmodel.ProdukViewModelFactory


sealed class TabItem(val route: String, val icon: ImageVector, val label: String) {
    object Kasir : TabItem("kasir", Icons.Default.ShoppingCart, "Kasir")
    object Pesanan : TabItem("pesanan", Icons.Default.Info, "Pesanan")
    object Produk : TabItem("produk", Icons.Default.Star, "Produk")
    object Laporan : TabItem("laporan", Icons.Default.Email, "Laporan")
    object Pengaturan : TabItem("pengaturan", Icons.Default.Settings, "Pengaturan")
}

@Composable
fun BottomNavigationBar(navController: androidx.navigation.NavHostController) {
    val items = listOf(
        TabItem.Kasir,
        TabItem.Pesanan,
        TabItem.Produk,
        TabItem.Laporan,
        TabItem.Pengaturan
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { navController.navigate(item.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                } },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label, fontSize = 10.sp, maxLines = 1) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                )
            )
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val showTabBar = remember { mutableStateOf(true) }
    Scaffold(
        bottomBar = { if (showTabBar.value) BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TabItem.Kasir.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(TabItem.Kasir.route) { KasirScreen(navController) }
            composable(TabItem.Pesanan.route) { PesananScreen(navController) }
            composable(TabItem.Produk.route) {
                val context = LocalContext.current.applicationContext
                val db = DatabaseProvider.getDatabase(context)
                val produkRepository = ProdukRepository(db.produkDao())
                val kategoriRepository = KategoriRepository(db.kategoriDao())
                val produkViewModel: id.stargan.shopos.viewmodel.ProdukViewModel = viewModel(
                    factory = ProdukViewModelFactory(produkRepository, kategoriRepository)
                )
                ProdukScreen(navController, produkViewModel)
            }
            composable(TabItem.Laporan.route) { LaporanScreen(navController) }
            composable(TabItem.Pengaturan.route) {
                PengaturanScreen(navController, showTabBar = { showTabBar.value = it })
            }
            composable("detail_pesanan") {
                DetailPesananScreen(navController, PesananSingleton.pesanan)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppNavigationPreview() {
    MaterialTheme {
        AppNavigation()
    }
}
