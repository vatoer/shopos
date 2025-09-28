package id.stargan.shopos.data.dao

import androidx.room.*
import id.stargan.shopos.data.entity.ProdukEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdukDao {
    @Query("SELECT * FROM produk ORDER BY nama")
    fun getAllProduk(): Flow<List<ProdukEntity>>

    @Query("SELECT * FROM produk WHERE kategoriId = :kategoriId ORDER BY nama")
    fun getProdukByKategori(kategoriId: Long): Flow<List<ProdukEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduk(produk: ProdukEntity)

    @Delete
    suspend fun deleteProduk(produk: ProdukEntity)
}

