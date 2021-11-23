package jrpricing.surcharge.domain.shared

import jrpricing.surcharge.domain.exception.DomainException
import jrpricing.surcharge.domain.exception.ErrorCode

/**
 * 移動経路クラス
 */
class TripRoute(
    val arrivalStationId: String,
    val departureStationId: String
) {
    init {
        if (arrivalStationId == "") throw DomainException("出発駅が指定されていません", ErrorCode.INVALID_INPUT)

        if (departureStationId == "") throw DomainException("到着駅が指定されていません", ErrorCode.INVALID_INPUT)
    }
}