package jrpricing.catalog.domain.model.seat

import jrpricing.catalog.domain.exception.DomainException

/**
 * 席種別
 */
enum class SeatType(val value: String, val id: Int) {
    RESERVED("指定席", 1),
    NON_RESERVED("自由席", 2);

    fun isReserve(): Boolean {
        return this === RESERVED
    }

    companion object {
        fun fromId(id: Int): SeatType {
            return values().firstOrNull { it.id == id }
                ?: throw DomainException("存在しない席種別です")
        }
    }
}