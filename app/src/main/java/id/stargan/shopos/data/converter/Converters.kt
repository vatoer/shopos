package id.stargan.shopos.data.converter

import androidx.room.TypeConverter
import id.stargan.shopos.data.entity.TipeDiskon
import id.stargan.shopos.data.entity.TipePromo

class Converters {
    // Converter untuk List<String>
    @TypeConverter
    fun fromStringList(value: String?): List<String>? {
        return value?.split(',')?.map { it.trim() }?.filter { it.isNotEmpty() }
    }

    @TypeConverter
    fun toStringList(list: List<String>?): String? {
        return list?.joinToString(",")
    }

    // Converter untuk List<Long>
    @TypeConverter
    fun fromLongList(value: String?): List<Long>? {
        return value?.split(',')?.mapNotNull { it.trim().toLongOrNull() }
    }

    @TypeConverter
    fun toLongList(list: List<Long>?): String? {
        return list?.joinToString(",") { it.toString() }
    }

    // Converter untuk TipeDiskon Enum
    @TypeConverter
    fun fromTipeDiskon(value: String?): TipeDiskon? {
        return value?.let { enumValueOf<TipeDiskon>(it) }
    }

    @TypeConverter
    fun tipeDiskonToString(tipeDiskon: TipeDiskon?): String? {
        return tipeDiskon?.name
    }

    // Converter untuk TipePromo Enum
    @TypeConverter
    fun fromTipePromo(value: String?): TipePromo? {
        return value?.let { enumValueOf<TipePromo>(it) }
    }

    @TypeConverter
    fun tipePromoToString(tipePromo: TipePromo?): String? {
        return tipePromo?.name
    }

}
