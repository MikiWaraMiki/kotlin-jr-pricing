package domain.model.payment

import domain.model.station.Station
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class RouteTest {

    @Test
    @DisplayName("出発駅と到着駅が同じ場合は同値と判定されること")
    fun isEqualWhenDepartureAndArrivalIsSame() {
        val aRoute = Route(
            DepartureStation(Station.TOKYO),
            ArrivalStation(Station.SHIN_OSAKA)
        )
        val sameRoute = Route(
            DepartureStation(Station.TOKYO),
            ArrivalStation(Station.SHIN_OSAKA)
        )

        Assertions.assertTrue(aRoute == sameRoute)
    }
}