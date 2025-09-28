package id.stargan.shopos.data.repository

import id.stargan.shopos.data.dao.KategoriProdukDao
import id.stargan.shopos.data.entity.KategoriProdukEntity
import kotlinx.coroutines.flow.Flow

class KategoriProdukRepository(private val dao: KategoriProdukDao) {
    fun getAllKategori(): Flow<List<KategoriProdukEntity>> = dao.getAllKategori()
    suspend fun addKategori(kategori: KategoriProdukEntity) = dao.insertKategori(kategori)
    suspend fun deleteKategori(kategori: KategoriProdukEntity) = dao.deleteKategori(kategori)
}

