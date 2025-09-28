package id.stargan.shopos.data.repository

import id.stargan.shopos.data.dao.DiskonDao
import id.stargan.shopos.data.entity.DiskonEntity
import kotlinx.coroutines.flow.Flow

class DiskonRepository(private val dao: DiskonDao) {
    fun getAllDiskon(): Flow<List<DiskonEntity>> = dao.getAllDiskon()
    suspend fun addDiskon(diskon: DiskonEntity) = dao.insertDiskon(diskon)
    suspend fun deleteDiskon(diskon: DiskonEntity) = dao.deleteDiskon(diskon)
}

