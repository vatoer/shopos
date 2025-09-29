package id.stargan.shopos.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import id.stargan.shopos.viewmodel.ProdukViewModel
import id.stargan.shopos.screens.produk.ProdukScreen as ProdukScreenModular

@Composable
fun ProdukScreen(navController: NavController, produkViewModel: ProdukViewModel) {
    ProdukScreenModular(navController, produkViewModel)
}
