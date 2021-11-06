package domain.model.fare

import domain.model.route.Route
import domain.model.shared.Passengers
import domain.model.shared.Price
import domain.model.ticket.DepartureDate
import domain.model.ticket.Ticket
import domain.model.ticket.TripType

/**
 * 運賃計算サービス
 */
class FareCalcService {
    private val fareTable = FareTable()

    fun calcPrice(
        route: Route,
        passengers: Passengers,
        departureDate: DepartureDate,
        tripType: TripType
    ): Price {
        val farePrice = fareTable.price(route)
        val fare = Fare(farePrice)

        // TODO 運賃割引に問い合わせ

        val fareCalcResult = FareCalcResult(fare, passengers)

        return fareCalcResult.total()
    }
}