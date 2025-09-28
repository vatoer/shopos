package id.stargan.shopos.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profil_toko")
data class ProfilTokoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val namaToko: String,
    val alamat: String,
    val telepon: String?,
    val logoUrl: String?
)

