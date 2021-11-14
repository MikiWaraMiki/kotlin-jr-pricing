package jrpricing.demo.first.domain.model.discount.testdata

import jrpricing.demo.first.domain.model.discount.FareDiscountCalcService
import jrpricing.demo.first.domain.model.shared.Passengers
import jrpricing.demo.first.domain.model.ticket.TripType

object TestFareDiscountCalcFactory {
    fun create(
        tripType: TripType = TripType.ROUND_TRIP,
        tripDistance: Int = 601,
        passengers: Passengers = Passengers(50, 0)
    ): FareDiscountCalcService {
        return FareDiscountCalcService.withGenerateRule(tripType, tripDistance, passengers)
    }
}