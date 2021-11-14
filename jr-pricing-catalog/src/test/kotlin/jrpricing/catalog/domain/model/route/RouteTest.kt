package jrpricing.catalog.domain.model.route

import jrpricing.catalog.domain.exception.DomainException
import jrpricing.catalog.domain.model.station.StationId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RouteTest {
    @Test
    fun `出発駅と到着駅が同じ場合はエラーになる`() {
        val routeId = RouteId.create()
        val stationId = StationId.create()

        val target: () -> Unit = {
            Route(routeId, stationId, stationId)
        }

        val exception = assertThrows<DomainException>(target)

        Assertions.assertEquals("出発駅と到着駅を同じにすることはできません", exception.message)
    }

    @Test
    fun `出発駅と到着駅が異なる場合はエラーにならないこと`() {
        val routeId = RouteId.create()
        val departureStationId = StationId.create()
        val arrivalStationId = StationId.create()

        val route = Route(routeId, departureStationId, arrivalStationId)

        Assertions.assertEquals(routeId, route.routeId)
        Assertions.assertEquals(departureStationId, route.departureStationId)
        Assertions.assertEquals(arrivalStationId, route.arrivalStationId)
    }
}