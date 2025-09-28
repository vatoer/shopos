package id.stargan.shopos.data.dao

import androidx.room.*
import id.stargan.shopos.data.entity.ProfilTokoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfilTokoDao {
    @Query("SELECT * FROM profil_toko LIMIT 1")
    fun getProfilToko(): Flow<ProfilTokoEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProfilToko(profil: ProfilTokoEntity)
}

