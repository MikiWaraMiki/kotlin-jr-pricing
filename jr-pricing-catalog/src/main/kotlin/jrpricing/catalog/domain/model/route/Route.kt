package jrpricing.catalog.domain.model.route

import jrpricing.catalog.domain.exception.DomainException
import jrpricing.catalog.domain.exception.ErrorCode
import jrpricing.catalog.domain.model.station.StationId

/**
 * 経路クラス
 */
class Route(
    val routeId: RouteId,
    val departureStationId: StationId,
    val arrivalStationId: StationId
    ){
    init {
        if (arrivalStationId.value == departureStationId.value) {
            throw DomainException("出発駅と到着駅を同じにすることはできません", ErrorCode.INVALID_INPUT)
        }
    }
}