package domain.model.fare

import domain.model.payment.ArrivalStation
import domain.model.payment.DepartureStation
import domain.model.shared.Route
import domain.model.station.Station

/**
 * 運賃表
 */
class FareTable {
    private val table = mapOf<Route, Fare>(
        Route(
            Station.TOKYO,
            Station.SHIN_OSAKA
        ) to Fare(8910),
        Route(
            Station.TOKYO,
            Station.HIMEJI
        ) to Fare(10010)
    )

    fun fare(route: Route): Fare {
        return table[route] ?: throw IllegalArgumentException(
            "運賃表に存在しない経路です。"
        )
    }
}