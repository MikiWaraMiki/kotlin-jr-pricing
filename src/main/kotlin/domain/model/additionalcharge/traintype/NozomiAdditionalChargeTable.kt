package domain.model.additionalcharge.traintype

import domain.model.shared.Price
import domain.model.shared.Route
import domain.model.station.Station

/**
 * のぞみ利用時の追加料金テーブル
 */
class NozomiAdditionalChargeTable() {
    private val table = mapOf<Route, Price>(
        Route(Station.TOKYO, Station.SHIN_OSAKA) to Price(320),
        Route(Station.TOKYO, Station.HIMEJI) to Price(530)
    )

    fun price(route: Route): Price {
        return table[route] ?: throw IllegalArgumentException(
            "存在しない経路です"
        )
    }
}