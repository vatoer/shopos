package id.stargan.shopos.data

import kotlinx.coroutines.flow.Flow

class KategoriRepository(private val kategoriDao: KategoriDao) {
    fun ambilSemuaKategori(): Flow<List<KategoriEntity>> = kategoriDao.ambilSemuaKategori()
    suspend fun tambahKategori(kategori: KategoriEntity) = kategoriDao.tambahKategori(kategori)
    suspend fun ubahKategori(kategori: KategoriEntity) = kategoriDao.ubahKategori(kategori)
    suspend fun hapusKategori(kategori: KategoriEntity) = kategoriDao.hapusKategori(kategori)
    suspend fun ambilKategoriById(id: Int): KategoriEntity? = kategoriDao.ambilKategoriById(id)
}

