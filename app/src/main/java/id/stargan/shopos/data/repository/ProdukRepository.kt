package id.stargan.shopos.data.repository

import id.stargan.shopos.data.dao.ProdukDao
import id.stargan.shopos.data.entity.ProdukEntity
import kotlinx.coroutines.flow.Flow

class ProdukRepository(private val dao: ProdukDao) {
    fun getAllProduk(): Flow<List<ProdukEntity>> = dao.getAllProduk()
    fun getProdukByKategori(kategoriId: Long): Flow<List<ProdukEntity>> = dao.getProdukByKategori(kategoriId)
    suspend fun addProduk(produk: ProdukEntity) = dao.insertProduk(produk)
    suspend fun deleteProduk(produk: ProdukEntity) = dao.deleteProduk(produk)
}

