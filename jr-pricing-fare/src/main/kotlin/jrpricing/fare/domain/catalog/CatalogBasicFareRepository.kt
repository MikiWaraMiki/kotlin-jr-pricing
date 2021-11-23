package jrpricing.fare.domain.catalog

import jrpricing.fare.domain.fare.BasicFare
import jrpricing.fare.domain.shared.Amount
import jrpricing.fare.domain.shared.TripRoute

interface CatalogBasicFareRepository {
    fun findBasicFare(tripRoute: TripRoute): BasicFare
}