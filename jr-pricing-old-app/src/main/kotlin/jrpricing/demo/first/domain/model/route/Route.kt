package jrpricing.demo.first.domain.model.route

import jrpricing.demo.first.domain.model.station.Station

/**
 * 経路
 */
class Route(
    aDepartureStation: Station,
    anArrivalStation: Station
) {
    val departureStation: Station
    val arrivalStation: Station

    private val IS_TOO_FAR_DISTANCE = 601 // km

    init {
        if (aDepartureStation == anArrivalStation)
            throw IllegalArgumentException("出発駅と到着駅は同じにすることはできません")

        departureStation = aDepartureStation
        arrivalStation = anArrivalStation
    }

    fun distance(): Int {
        return RouteDistanceTable.distance(this)
    }

    /**
     * 片道の移動距離が601km以上か判定する
     */
    fun isLongDistance(): Boolean {
        val distance = RouteDistanceTable.distance(this)

        return distance >= 601
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (javaClass != other?.javaClass) return false

        other as Route

        if (departureStation == other.departureStation && arrivalStation == other.arrivalStation)
            return true

        return false
    }

    override fun hashCode(): Int {
        return (departureStation.hashCode() + arrivalStation.hashCode()) * 31
    }
}