package id.stargan.shopos.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diskon")
data class DiskonEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nama: String,
    val persentase: Double,
    val aktif: Boolean = true
)

