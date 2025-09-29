package id.stargan.shopos.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kategori_produk")
data class KategoriProdukEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nama: String,
    val deskripsi: String?
)

