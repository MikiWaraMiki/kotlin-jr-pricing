package domain.model.fare

import domain.model.shared.Price
import domain.model.route.Route
import domain.model.shared.Passengers
import domain.model.station.Station
import org.junit.jupiter.api.*

class FareCalcServiceTest {
    private val fareCalcService = FareCalcService()
    private val ADULT_TOKYO_SHINOSAKA_PRICE = 8910

    @Nested
    inner class CalcPriceTest() {
        @Test
        fun `存在する経路の場合は合計料金が取得できること`() {
            val passengers = Passengers(1,0)
            val route = Route(Station.TOKYO, Station.SHIN_OSAKA)
            val fareCalcService = FareCalcService()

            val result = fareCalcService.calcPrice(route, passengers)

            val expected = Price(ADULT_TOKYO_SHINOSAKA_PRICE)
            Assertions.assertEquals(expected, result)
        }

        @Test
        fun `存在しない経路の場合は、例外が発生すること`() {
            val passengers = Passengers(1, 0)
            val route = Route(Station.SHIN_OSAKA, Station.HIMEJI)
            val fareCalcService = FareCalcService()

            val target: () -> Unit = {
                fareCalcService.calcPrice(route, passengers)
            }

            val exception = assertThrows<IllegalArgumentException>(target)
            Assertions.assertEquals("運賃表に存在しない経路です。", exception.message)
        }
    }

}