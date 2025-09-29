package id.stargan.shopos.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdukDao {
    @Query("SELECT * FROM produk")
    fun ambilSemuaProduk(): Flow<List<ProdukEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun tambahProduk(produk: ProdukEntity)

    @Update
    suspend fun ubahProduk(produk: ProdukEntity)

    @Delete
    suspend fun hapusProduk(produk: ProdukEntity)

    @Query("SELECT * FROM produk WHERE id = :id")
    suspend fun ambilProdukById(id: Int): ProdukEntity?

    @Query("SELECT * FROM produk WHERE sku = :sku")
    suspend fun ambilProdukBySku(sku: String): ProdukEntity?
}

