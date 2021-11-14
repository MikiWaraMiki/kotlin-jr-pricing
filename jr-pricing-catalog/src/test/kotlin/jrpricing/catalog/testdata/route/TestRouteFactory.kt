package jrpricing.catalog.testdata.route

import jrpricing.catalog.domain.model.route.Route
import jrpricing.catalog.domain.model.route.RouteId
import jrpricing.catalog.domain.model.station.StationId

object TestRouteFactory {
    fun create(
        routeId: RouteId = RouteId.create(),
        departureStationId: StationId = StationId.create(),
        arrivalStationId: StationId = StationId.create()
    ): Route {
        return Route(
            routeId,
            departureStationId,
            arrivalStationId
        )
    }
}