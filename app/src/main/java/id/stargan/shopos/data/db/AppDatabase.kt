package id.stargan.shopos.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.stargan.shopos.data.converter.Converters
import id.stargan.shopos.data.dao.DiskonDao
import id.stargan.shopos.data.dao.KategoriProdukDao
import id.stargan.shopos.data.dao.PenggunaDao
import id.stargan.shopos.data.dao.ProfilTokoDao
import id.stargan.shopos.data.dao.PromoDao
import id.stargan.shopos.data.entity.DiskonEntity
import id.stargan.shopos.data.entity.KategoriProdukEntity
import id.stargan.shopos.data.entity.PenggunaEntity
import id.stargan.shopos.data.entity.ProfilTokoEntity
import id.stargan.shopos.data.entity.PromoEntity

@Database(
    entities = [
        ProfilTokoEntity::class,
        PenggunaEntity::class,
        KategoriProdukEntity::class,
        DiskonEntity::class,
        PromoEntity::class,
        // Tambahkan entitas lain di sini jika ada, misal: ProdukEntity, OrderEntity, dll.
    ],
    version = 1, // Naikkan versi jika Anda mengubah skema di masa mendatang
    exportSchema = true // Ekspor skema ke file JSON (baik untuk version control dan migrasi)
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun profilTokoDao(): ProfilTokoDao
    abstract fun penggunaDao(): PenggunaDao
    abstract fun kategoriProdukDao(): KategoriProdukDao
    abstract fun diskonDao(): DiskonDao
    abstract fun promoDao(): PromoDao
    // Tambahkan abstract fun untuk DAO lain di sini

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "shopos_database"
                )
                // Jika Anda memerlukan data awal (pre-populate)
                // .createFromAsset("database/shopos.db")
                // Jika Anda memerlukan strategi migrasi
                // .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .fallbackToDestructiveMigration() // Hati-hati: ini akan menghapus DB jika migrasi tidak ditemukan
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
