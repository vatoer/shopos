package id.stargan.shopos.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "produk",
    foreignKeys = [
        ForeignKey(
            entity = KategoriProdukEntity::class,
            parentColumns = ["id"],
            childColumns = ["kategoriId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProdukEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sku: String,
    val nama: String,
    val harga: Int,
    val kategoriId: Long,
    val stok: Int
)
