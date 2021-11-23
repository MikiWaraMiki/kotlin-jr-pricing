package jrpricing.fare.domain.catalog

import jrpricing.fare.domain.shared.Amount

interface CatalogBasicFareRepository {
    fun findBasicFare(arrivalStationId: String, departureStationId: String): Amount
}