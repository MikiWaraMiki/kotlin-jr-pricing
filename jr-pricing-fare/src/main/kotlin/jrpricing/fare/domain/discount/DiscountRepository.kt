package jrpricing.fare.domain.discount

import jrpricing.fare.domain.fare.BasicFare
import jrpricing.fare.domain.shared.Amount
import jrpricing.fare.domain.shared.DepartureDate
import jrpricing.fare.domain.shared.Passenger
import jrpricing.fare.domain.shared.TripRoute

interface DiscountRepository {
    fun calc(basicFare: BasicFare, route: TripRoute, passenger: Passenger, departureDate: DepartureDate): Amount
}