package jrpricing.catalog.domain.model.fare

import jrpricing.catalog.domain.model.route.RouteId

interface BasicFareRepository {
    fun insert(basicFare: BasicFare)
    fun findByRouteId(routeId: RouteId): BasicFare?
}