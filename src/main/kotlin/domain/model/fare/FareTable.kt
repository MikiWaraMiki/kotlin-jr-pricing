package domain.model.fare

import domain.model.payment.ArrivalStation
import domain.model.payment.DepartureStation
import domain.model.shared.Route
import domain.model.station.Station

/**
 * 運賃表
 */
// TODO: [domain.model.payment.Route]に依存するのは違和感がある
class FareTable {
    private val table = mapOf<Route, Int>(
        Route(
            Station.TOKYO,
            Station.SHIN_OSAKA
        ) to 8910,
        Route(
            Station.TOKYO,
            Station.HIMEJI
        ) to 10010
    )

    fun fare(route: Route): Int {
        return table[route] ?: throw IllegalArgumentException(
            "運賃表に存在しない経路です。"
        )
    }
}