package id.stargan.shopos.data.dao

import androidx.room.*
import id.stargan.shopos.data.entity.PenggunaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PenggunaDao {
    @Query("SELECT * FROM pengguna ORDER BY nama")
    fun getAllPengguna(): Flow<List<PenggunaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPengguna(pengguna: PenggunaEntity)

    @Delete
    suspend fun deletePengguna(pengguna: PenggunaEntity)
}

