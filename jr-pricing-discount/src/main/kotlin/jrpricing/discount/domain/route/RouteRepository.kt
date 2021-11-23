package jrpricing.discount.domain.route

import jrpricing.discount.domain.route.Route

interface RouteRepository {
    fun findByStationId(departureStationId: String, arrivalStationId: String): Route?
}