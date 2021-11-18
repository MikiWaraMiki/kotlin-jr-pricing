package jrpricing.catalog.domain.model.surcharge

import jrpricing.catalog.domain.model.route.RouteId

interface BasicSurchargeRepository {
    fun insert(basicSurcharge: BasicSurcharge)
    fun findByRouteId(routeId: RouteId): BasicSurcharge?
}