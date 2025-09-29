package id.stargan.shopos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.stargan.shopos.data.ProdukRepository
import id.stargan.shopos.data.KategoriRepository

class ProdukViewModelFactory(
    private val produkRepository: ProdukRepository,
    private val kategoriRepository: KategoriRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProdukViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProdukViewModel(produkRepository, kategoriRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

