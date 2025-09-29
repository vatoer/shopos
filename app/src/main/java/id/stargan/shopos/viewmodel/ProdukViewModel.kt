package id.stargan.shopos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.stargan.shopos.data.ProdukEntity
import id.stargan.shopos.data.ProdukRepository
import id.stargan.shopos.data.KategoriEntity
import id.stargan.shopos.data.KategoriRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProdukViewModel(
    private val produkRepository: ProdukRepository,
    private val kategoriRepository: KategoriRepository
) : ViewModel() {
    private val _daftarProduk = MutableStateFlow<List<ProdukEntity>>(emptyList())
    val daftarProduk: StateFlow<List<ProdukEntity>> = _daftarProduk.asStateFlow()

    private val _daftarKategori = MutableStateFlow<List<KategoriEntity>>(emptyList())
    val daftarKategori: StateFlow<List<KategoriEntity>> = _daftarKategori.asStateFlow()

    init {
        ambilDaftarProduk()
        ambilDaftarKategori()
    }

    fun ambilDaftarProduk() {
        viewModelScope.launch {
            produkRepository.ambilSemuaProduk().collectLatest {
                _daftarProduk.value = it
            }
        }
    }

    fun ambilDaftarKategori() {
        viewModelScope.launch {
            kategoriRepository.ambilSemuaKategori().collectLatest {
                _daftarKategori.value = it
            }
        }
    }

    fun tambahProduk(produk: ProdukEntity) {
        viewModelScope.launch {
            produkRepository.tambahProduk(produk)
            ambilDaftarProduk()
        }
    }

    fun ubahProduk(produk: ProdukEntity) {
        viewModelScope.launch {
            produkRepository.ubahProduk(produk)
            ambilDaftarProduk()
        }
    }

    fun hapusProduk(produk: ProdukEntity) {
        viewModelScope.launch {
            produkRepository.hapusProduk(produk)
            ambilDaftarProduk()
        }
    }
}
