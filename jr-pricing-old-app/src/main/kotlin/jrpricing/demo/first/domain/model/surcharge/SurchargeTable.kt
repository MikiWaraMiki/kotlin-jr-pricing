package jrpricing.demo.first.domain.model.surcharge

import jrpricing.demo.first.domain.model.route.Route
import jrpricing.demo.first.domain.model.shared.Price
import jrpricing.demo.first.domain.model.station.Station

/**
 * 特急料金表クラス
 */
class SurchargeTable {
    private val surchargeTable = mapOf<Route, Price>(
        Route(Station.TOKYO, Station.SHIN_OSAKA) to Price(5490),
        Route(Station.TOKYO, Station.HIMEJI) to Price(5920)
    )

    fun price(route: Route): Price {
        return surchargeTable[route] ?: throw IllegalArgumentException("料金表に存在しません")
    }
}