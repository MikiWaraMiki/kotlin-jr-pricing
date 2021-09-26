package domain.model.fare

import domain.model.shared.Price
import domain.model.shared.Route
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
            Station.TOKYO,
            Station.SHIN_OSAKA
        )

        val price = fareTable.price(route)

        Assertions.assertEquals(Price(8910), price)
    }

    @Test
    @DisplayName("東京から姫路の運賃が10010円であること")
    fun tokyoToHimejiValidFare() {
        val route = Route(
            Station.TOKYO,
            Station.HIMEJI
        )

        val price = fareTable.price(route)

        Assertions.assertEquals(Price(10010), price)
    }
}