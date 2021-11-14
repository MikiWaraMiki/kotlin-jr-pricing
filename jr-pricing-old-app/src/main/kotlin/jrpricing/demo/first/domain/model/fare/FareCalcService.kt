package jrpricing.demo.first.domain.model.fare

import jrpricing.demo.first.domain.model.discount.FareDiscountCalcService
import jrpricing.demo.first.domain.model.route.Route
import jrpricing.demo.first.domain.model.shared.Passengers
import jrpricing.demo.first.domain.model.ticket.DepartureDate
import jrpricing.demo.first.domain.model.ticket.TripType

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