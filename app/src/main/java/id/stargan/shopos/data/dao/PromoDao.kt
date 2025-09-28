package id.stargan.shopos.data.dao

import androidx.room.*
import id.stargan.shopos.data.entity.PromoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PromoDao {
    @Query("SELECT * FROM promo ORDER BY nama")
    fun getAllPromo(): Flow<List<PromoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPromo(promo: PromoEntity)

    @Delete
    suspend fun deletePromo(promo: PromoEntity)
}

