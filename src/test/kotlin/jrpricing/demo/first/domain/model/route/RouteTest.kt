package jrpricing.demo.first.domain.model.route

import jrpricing.demo.first.domain.model.route.Route
import jrpricing.demo.first.domain.model.station.Station
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class RouteTest {

    @Test
    @DisplayName("到着駅と出発駅が同じ場合は同値であること")
    fun equalsWhenDepartureStationAndArrivalStationIsSame() {
        val aRoute = Route(
            Station.TOKYO,
            Station.HIMEJI
        )
        val sameRoute = Route(
            Station.TOKYO,
            Station.HIMEJI
        )

        Assertions.assertTrue(aRoute == sameRoute)
    }

    @Test
    @DisplayName("出発駅が異なる場合は同値ではないこと")
    fun notEqualsWhenArrivalIsSameButDepartureIsDifferent() {
        val aRoute = Route(
            Station.TOKYO,
            Station.HIMEJI
        )
        val notDifferentRoute = Route(
            Station.SHIN_OSAKA,
            Station.HIMEJI
        )

        Assertions.assertFalse(aRoute == notDifferentRoute)
    }

    @Test
    @DisplayName("到着駅が異なる場合は同値でないこと")
    fun notEqualsWhenDepartureIsSameButArrivalIsDifferent() {
        val aRoute = Route(
            Station.TOKYO,
            Station.SHIN_OSAKA
        )

        val notDifferentRoute = Route(
            Station.HIMEJI,
            Station.SHIN_OSAKA
        )

        Assertions.assertFalse(aRoute == notDifferentRoute)
    }
}