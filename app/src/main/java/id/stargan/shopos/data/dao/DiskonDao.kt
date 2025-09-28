package id.stargan.shopos.data.dao

import androidx.room.*
import id.stargan.shopos.data.entity.DiskonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DiskonDao {
    @Query("SELECT * FROM diskon ORDER BY nama")
    fun getAllDiskon(): Flow<List<DiskonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiskon(diskon: DiskonEntity)

    @Delete
    suspend fun deleteDiskon(diskon: DiskonEntity)
}

