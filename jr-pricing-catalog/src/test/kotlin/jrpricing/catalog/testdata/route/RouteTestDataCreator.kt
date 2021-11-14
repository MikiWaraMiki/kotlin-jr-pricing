package jrpricing.catalog.testdata.route

import jrpricing.catalog.domain.model.route.Route
import jrpricing.catalog.domain.model.route.RouteRepository
import jrpricing.catalog.domain.model.station.Station
import jrpricing.catalog.domain.model.station.StationId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RouteTestDataCreator(
    @Autowired private val routeRepository: RouteRepository
) {
    fun create(
        departureStationId: StationId = StationId.create(),
        arrivalStationId: StationId = StationId.create()
    ): Route {
        val route = TestRouteFactory.create(
            departureStationId = departureStationId,
            arrivalStationId = arrivalStationId
        )

        routeRepository.insert(route)

        return route
    }
}