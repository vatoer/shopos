package id.stargan.shopos.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface KategoriDao {
    @Query("SELECT * FROM kategori")
    fun ambilSemuaKategori(): Flow<List<KategoriEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun tambahKategori(kategori: KategoriEntity)

    @Update
    suspend fun ubahKategori(kategori: KategoriEntity)

    @Delete
    suspend fun hapusKategori(kategori: KategoriEntity)

    @Query("SELECT * FROM kategori WHERE id = :id")
    suspend fun ambilKategoriById(id: Int): KategoriEntity?
}

