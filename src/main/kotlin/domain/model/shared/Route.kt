package domain.model.shared

import domain.model.station.Station

/**
 * 経路
 */
class Route(
    aDepartureStation: Station,
    anArrivalStation: Station
) {
    val departureStation: Station
    val arrivalStation: Station

    init {
        if (aDepartureStation == anArrivalStation)
            throw IllegalArgumentException("出発駅と到着駅は同じにすることはできません")

        departureStation = aDepartureStation
        arrivalStation = anArrivalStation
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