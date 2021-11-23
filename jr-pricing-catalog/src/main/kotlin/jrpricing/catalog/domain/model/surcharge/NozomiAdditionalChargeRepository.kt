package jrpricing.catalog.domain.model.surcharge

import jrpricing.catalog.domain.model.route.RouteId

interface NozomiAdditionalChargeRepository {
    fun insert(nozomiAdditionalCharge: NozomiAdditionalCharge)
    fun findByRouteId(routeId: RouteId): NozomiAdditionalCharge?
}