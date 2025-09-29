package id.stargan.shopos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.stargan.shopos.data.ProdukEntity
import id.stargan.shopos.data.ProdukRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class KasirViewModel(
    private val produkRepository: ProdukRepository? = null
) : ViewModel() {
    private val _daftarProduk = MutableStateFlow<List<ProdukEntity>>(emptyList())
    val daftarProduk: StateFlow<List<ProdukEntity>> = _daftarProduk.asStateFlow()

    init {
        if (produkRepository != null) {
            ambilDaftarProduk()
        }
    }

    fun ambilDaftarProduk() {
        produkRepository?.let {
            viewModelScope.launch {
                it.ambilSemuaProduk().collectLatest { produkList ->
                    _daftarProduk.value = produkList
                }
            }
        }
    }

    // Untuk preview
    fun setDummyProduk(dummyProduk: List<ProdukEntity>) {
        _daftarProduk.value = dummyProduk
    }

    companion object {
        fun preview(dummyProduk: List<ProdukEntity>): KasirViewModel {
            val vm = KasirViewModel(null)
            vm.setDummyProduk(dummyProduk)
            return vm
        }
    }
}
