package domain.model.payment

import domain.model.station.Station


/**
 * 出発駅
 */
class DepartureStation(val aStation: Station) {
    val station: Station

    init {
        if (aStation != Station.TOKYO)
            throw IllegalArgumentException("出発駅は東京のみ選択できます")

        station = aStation
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DepartureStation

        if (aStation != other.station) return false

        return true
    }

    override fun hashCode(): Int {
        return station.name.hashCode()
    }
}