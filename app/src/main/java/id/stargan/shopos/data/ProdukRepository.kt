package id.stargan.shopos.data

import kotlinx.coroutines.flow.Flow

class ProdukRepository(private val produkDao: ProdukDao) {
    fun ambilSemuaProduk(): Flow<List<ProdukEntity>> = produkDao.ambilSemuaProduk()
    suspend fun tambahProduk(produk: ProdukEntity) = produkDao.tambahProduk(produk)
    suspend fun ubahProduk(produk: ProdukEntity) = produkDao.ubahProduk(produk)
    suspend fun hapusProduk(produk: ProdukEntity) = produkDao.hapusProduk(produk)
    suspend fun ambilProdukById(id: Int): ProdukEntity? = produkDao.ambilProdukById(id)
    suspend fun ambilProdukBySku(sku: String): ProdukEntity? = produkDao.ambilProdukBySku(sku)
}

