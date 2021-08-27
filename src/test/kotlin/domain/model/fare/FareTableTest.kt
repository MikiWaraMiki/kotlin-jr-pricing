package domain.model.fare

import domain.model.payment.ArrivalStation
import domain.model.payment.DepartureStation
import domain.model.payment.Route
import domain.model.station.Station
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class FareTableTest {
    private val fareTable = FareTable()

    @Test
    @DisplayName("東京から新大阪の運賃が8910円であること")
    fun tokyoToShinOsakaValidFare() {
        val route = Route(
            DepartureStation(Station.TOKYO),
            ArrivalStation(Station.SHIN_OSAKA)
        )

        val result = fareTable.fare(route)

        Assertions.assertEquals(8910, result)
    }

    @Test
    @DisplayName("東京から姫路の運賃が10010円であること")
    fun tokyoToHimejiValidFare() {
        val route = Route(
            DepartureStation(Station.TOKYO),
            ArrivalStation(Station.HIMEJI)
        )

        val result = fareTable.fare(route)

        Assertions.assertEquals(10010, result)
    }
}