package id.stargan.shopos.data.repository

import id.stargan.shopos.data.dao.PenggunaDao
import id.stargan.shopos.data.entity.PenggunaEntity
import kotlinx.coroutines.flow.Flow

class PenggunaRepository(private val dao: PenggunaDao) {
    fun getAllPengguna(): Flow<List<PenggunaEntity>> = dao.getAllPengguna()
    suspend fun addPengguna(pengguna: PenggunaEntity) = dao.insertPengguna(pengguna)
    suspend fun deletePengguna(pengguna: PenggunaEntity) = dao.deletePengguna(pengguna)
}

