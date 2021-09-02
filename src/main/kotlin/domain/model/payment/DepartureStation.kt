package domain.model.payment

import domain.model.station.Station


/**
 * 出発駅
 */
class DepartureStation(aStation: Station) {
    val station: Station

    init {
        if (aStation != Station.TOKYO)
            throw IllegalArgumentException("出発駅は東京のみ選択できます")

        station = aStation
    }
}