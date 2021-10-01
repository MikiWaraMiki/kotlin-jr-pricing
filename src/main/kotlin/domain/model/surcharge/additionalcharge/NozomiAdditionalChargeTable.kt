package domain.model.surcharge.additionalcharge

import domain.model.shared.Price
import domain.model.route.Route
import domain.model.station.Station

/**
 * のぞみ利用時の追加料金表
 */
class NozomiAdditionalChargeTable() {
    private val table = mapOf<Route, Price>(
        Route(Station.TOKYO, Station.SHIN_OSAKA) to Price(320),
        Route(Station.TOKYO, Station.HIMEJI) to Price(530)
    )

    fun price(route: Route): Price {
        return table[route] ?: throw IllegalArgumentException(
            "料金表に存在しません"
        )
    }
}