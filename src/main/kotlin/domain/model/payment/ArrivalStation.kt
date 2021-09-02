package domain.model.payment

import domain.model.station.Station

/**
 * 到着駅
 */
class ArrivalStation(aStation: Station) {
    val station: Station

    init {
        if (aStation != Station.HIMEJI && aStation != Station.SHIN_OSAKA)
            throw IllegalArgumentException("到着駅は、姫路もしくは新大阪が選択可能です。")

        station = aStation
    }
}