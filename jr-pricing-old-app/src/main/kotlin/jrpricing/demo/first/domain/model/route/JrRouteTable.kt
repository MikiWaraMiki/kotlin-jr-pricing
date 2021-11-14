package jrpricing.demo.first.domain.model.route

import jrpricing.demo.first.domain.model.exception.DomainException
import jrpricing.demo.first.domain.model.exception.ErrorCode
import jrpricing.demo.first.domain.model.station.Station

/**
 * JR移動可能な経路が登録された表
 */
class JrRouteTable {
    private val table = mapOf<Station, List<Station>>(
        Station.TOKYO to listOf<Station>(Station.HIMEJI, Station.SHIN_OSAKA)
    )

    companion object {
        fun of(departureStation: Station, arrivalStation: Station): Route {
            val routeTable = JrRouteTable()

            val arrivalStationList = routeTable.table[departureStation] ?:
            throw DomainException("経路表に登録されていない出発駅です", ErrorCode.INVALID_INPUT)

            if (!arrivalStationList.contains(arrivalStation)) {
                throw DomainException("経路表に登録されていない到着駅です", ErrorCode.INVALID_INPUT)
            }

            return Route(departureStation, arrivalStation)

        }
    }
}