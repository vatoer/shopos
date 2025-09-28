package id.stargan.shopos.data.repository

import id.stargan.shopos.data.dao.ProfilTokoDao
import id.stargan.shopos.data.entity.ProfilTokoEntity
import kotlinx.coroutines.flow.Flow

class ProfilTokoRepository(private val dao: ProfilTokoDao) {
    fun getProfilToko(): Flow<ProfilTokoEntity?> = dao.getProfilToko()
    suspend fun saveProfilToko(profil: ProfilTokoEntity) = dao.insertOrUpdateProfilToko(profil)
}

