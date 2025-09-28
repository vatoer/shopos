package id.stargan.shopos.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pengguna")
data class PenggunaEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nama: String,
    val email: String?,
    val role: String,
    val aktif: Boolean = true
)

