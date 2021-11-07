package jrpricing.demo.first.domain.model.fare

import jrpricing.demo.first.domain.model.shared.Price
import jrpricing.demo.first.domain.model.route.Route
import jrpricing.demo.first.domain.model.station.Station

/**
 * 運賃表
 */
class FareTable {
    private val table = mapOf<Route, Price>(
        Route(
            Station.TOKYO,
            Station.SHIN_OSAKA
        ) to Price(8910),
        Route(
            Station.TOKYO,
            Station.HIMEJI
        ) to Price(10010)
    )

    fun price(route: Route): Price {
        return table[route] ?: throw IllegalArgumentException(
            "運賃表に存在しない経路です。"
        )
    }
}