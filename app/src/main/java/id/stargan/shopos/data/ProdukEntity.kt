package id.stargan.shopos.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.ForeignKey

@Entity(tableName = "produk",
    foreignKeys = [
        ForeignKey(
            entity = KategoriEntity::class,
            parentColumns = ["id"],
            childColumns = ["kategoriId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProdukEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "sku")
    val sku: String,
    @ColumnInfo(name = "nama")
    val nama: String,
    @ColumnInfo(name = "kategoriId")
    val kategoriId: Int,
    @ColumnInfo(name = "harga")
    val harga: Int,
    @ColumnInfo(name = "stok")
    val stok: Int
)

