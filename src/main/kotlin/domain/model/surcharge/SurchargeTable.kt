package domain.model.surcharge

import domain.model.route.Route
import domain.model.shared.Price
import domain.model.station.Station
import domain.model.train.TrainType

/**
 * 特急料金表クラス
 */
class SurchargeTable() {
    private val surchargeTable = mapOf<Route, Price>(
        Route(Station.TOKYO, Station.SHIN_OSAKA) to Price(5490),
        Route(Station.TOKYO, Station.HIMEJI) to Price(5920)
    )

    fun price(route: Route): Price {
        return surchargeTable[route] ?: throw IllegalArgumentException("料金表に存在しません")
    }
}