package id.stargan.shopos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.stargan.shopos.data.ProdukRepository

class KasirViewModelFactory(
    private val produkRepository: ProdukRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KasirViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return KasirViewModel(produkRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

