package id.stargan.shopos.data.repository

import id.stargan.shopos.data.dao.PromoDao
import id.stargan.shopos.data.entity.PromoEntity
import kotlinx.coroutines.flow.Flow

class PromoRepository(private val dao: PromoDao) {
    fun getAllPromo(): Flow<List<PromoEntity>> = dao.getAllPromo()
    suspend fun addPromo(promo: PromoEntity) = dao.insertPromo(promo)
    suspend fun deletePromo(promo: PromoEntity) = dao.deletePromo(promo)
}

