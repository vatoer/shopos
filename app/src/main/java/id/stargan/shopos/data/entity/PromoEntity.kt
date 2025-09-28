package id.stargan.shopos.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "promo")
data class PromoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nama: String,
    val deskripsi: String?,
    val mulai: Long,
    val selesai: Long,
    val aktif: Boolean = true
)

