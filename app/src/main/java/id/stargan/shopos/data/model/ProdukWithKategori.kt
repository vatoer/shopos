package id.stargan.shopos.data.model

import androidx.room.Embedded
import androidx.room.Relation
import id.stargan.shopos.data.entity.ProdukEntity
import id.stargan.shopos.data.entity.KategoriProdukEntity

// For UI: join produk with kategori

data class ProdukWithKategori(
    @Embedded val produk: ProdukEntity,
    @Relation(
        parentColumn = "kategoriId",
        entityColumn = "id"
    )
    val kategori: KategoriProdukEntity?
)

