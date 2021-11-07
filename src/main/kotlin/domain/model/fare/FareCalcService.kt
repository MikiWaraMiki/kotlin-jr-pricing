package domain.model.fare

import domain.model.discount.FareDiscountCalcService
import domain.model.route.Route
import domain.model.shared.Passengers
import domain.model.shared.Price
import domain.model.ticket.DepartureDate
import domain.model.ticket.TripType

/**
 * 運賃計算サービス
 */
class FareCalcService(
    private val fareDiscountCalcService: FareDiscountCalcService
) {
    private val fareTable = FareTable()

    fun calcPrice(
        route: Route,
        passengers: Passengers,
        departureDate: DepartureDate,
        tripType: TripType
    ): FareCalcResult {
        val farePrice = fareTable.price(route)
        val fare = Fare(farePrice)

        val beforeDiscountedFare = BeforeDiscountedFare.from(fare, passengers)

        val discountResult = fareDiscountCalcService.calc(fare, passengers, departureDate)
        val afterDiscountedFare = AfterDiscountedFare(discountResult)

        return FareCalcResult(beforeDiscountedFare, afterDiscountedFare, tripType)
    }
}