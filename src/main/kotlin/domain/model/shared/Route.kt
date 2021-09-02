package domain.model.shared

import domain.model.station.Station

/**
 * 経路
 */
// TODO: 出発地クラスと到着地クラスは必要ないかもしれない
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
}