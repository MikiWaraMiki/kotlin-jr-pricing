package domain.model.fare

import domain.model.route.Route
import domain.model.shared.Passengers
import domain.model.shared.Price
import domain.model.ticket.Ticket

/**
 * 運賃計算サービス
 */
class FareCalcService {
    private val fareTable = FareTable()

    fun calcPrice(route: Route, passengers: Passengers): Price {
        val farePrice = fareTable.price(route)
        val fare = Fare(farePrice)

        val fareCalcResult = FareCalcResult(fare, passengers)

        return fareCalcResult.total()
    }
}