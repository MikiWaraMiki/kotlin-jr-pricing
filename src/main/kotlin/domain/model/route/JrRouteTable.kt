package domain.model.route

import domain.model.station.Station

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
            throw IllegalArgumentException("経路表に登録されていない出発駅です")

            if (!arrivalStationList.contains(arrivalStation)) {
                throw IllegalArgumentException("経路表に登録されていない到着駅です")
            }

            return Route(departureStation, arrivalStation)

        }
    }
}