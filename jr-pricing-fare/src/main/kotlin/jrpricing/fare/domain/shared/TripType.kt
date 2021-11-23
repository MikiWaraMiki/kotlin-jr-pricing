package jrpricing.fare.domain.shared

import jrpricing.fare.domain.exception.DomainException
import jrpricing.fare.domain.exception.ErrorCode

/**
 * 移動種別
 */
enum class TripType(val typeName: String) {
    HALF_WAY("half_way"),
    ROUND_TRIP("round_trip");

    fun isRoundTrip(): Boolean {
        return this === ROUND_TRIP
    }

    companion object {
        fun of(typeName: String): TripType {
            return values().firstOrNull { it.typeName == typeName }
                ?: throw DomainException("片道もしくは往復を選択してください", ErrorCode.INVALID_INPUT)
        }
    }
}