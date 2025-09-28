package id.stargan.shopos.data.dao

import androidx.room.*
import id.stargan.shopos.data.entity.KategoriProdukEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface KategoriProdukDao {
    @Query("SELECT * FROM kategori_produk ORDER BY nama")
    fun getAllKategori(): Flow<List<KategoriProdukEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKategori(kategori: KategoriProdukEntity)

    @Delete
    suspend fun deleteKategori(kategori: KategoriProdukEntity)
}

