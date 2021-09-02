package domain.model.fare

import domain.model.shared.Route
import domain.model.station.Station
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class FareCalcServiceTest {

    @Test
    @DisplayName("東京→新大阪にかかる運賃計算を要求した際に8,910を返すこと")
    fun resultIs8910WhenTokyoToOsaka() {
        val route = Route(
            Station.TOKYO,
            Station.SHIN_OSAKA
        )

        val fareResult = FareCalcService.calcFare(route)

        Assertions.assertEquals(8910, fareResult)
    }

    @Test
    @DisplayName("東京→姫路にかかる運賃計算を要求した際に10,010を返すこと")
    fun resultIs10010WhenTokyoToHimeji() {
        val route = Route(
            Station.TOKYO,
            Station.HIMEJI
        )

        val fareResult = FareCalcService.calcFare(route)

        Assertions.assertEquals(10010, fareResult)
    }
}