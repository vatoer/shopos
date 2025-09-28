package id.stargan.shopos

import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.stargan.shopos.screens.KasirScreen
import id.stargan.shopos.screens.LaporanScreen
import id.stargan.shopos.screens.PengaturanScreen
import id.stargan.shopos.screens.ProdukScreen
import id.stargan.shopos.screens.PesananScreen
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState

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
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TabItem.Kasir.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(TabItem.Kasir.route) { KasirScreen(navController) }
            composable(TabItem.Pesanan.route) { PesananScreen(navController) }
            composable(TabItem.Produk.route) { ProdukScreen(navController) }
            composable(TabItem.Laporan.route) { LaporanScreen(navController) }
            composable(TabItem.Pengaturan.route) { PengaturanScreen(navController) }
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
