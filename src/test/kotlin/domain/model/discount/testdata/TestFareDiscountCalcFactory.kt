package domain.model.discount.testdata

import domain.model.discount.FareDiscountCalcService
import domain.model.fare.FareCalcService
import domain.model.shared.Passengers
import domain.model.ticket.TripType

object TestFareDiscountCalcFactory {
    fun create(
        tripType: TripType = TripType.ROUND_TRIP,
        tripDistance: Int = 601,
        passengers: Passengers = Passengers(50, 0)
    ): FareDiscountCalcService {
        return FareDiscountCalcService.withGenerateRule(tripType, tripDistance, passengers)
    }
}