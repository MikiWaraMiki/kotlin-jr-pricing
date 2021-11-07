package domain.model.fare

import domain.model.discount.FareDiscountCalcService
import domain.model.shared.Price
import domain.model.route.Route
import domain.model.shared.Passengers
import domain.model.station.Station
import domain.model.ticket.DepartureDate
import domain.model.ticket.TripType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.*

class FareCalcServiceTest {
    private val ADULT_TOKYO_SHINOSAKA_PRICE = 8910
    private val mockFareDiscountCalcService: FareDiscountCalcService = mockk()

    @Nested
    inner class CalcPriceTest() {
        @Test
        fun `片道の移動で、割引されない場合の運賃合計金額が正しいこと`() {
            val passengers = Passengers(1, 0)
            val route = Route(Station.TOKYO, Station.SHIN_OSAKA)
            val departureDate = DepartureDate.of("2021-12-21")

            every { mockFareDiscountCalcService.calc(allAny(), allAny(), allAny()) }.returns(
                Price(ADULT_TOKYO_SHINOSAKA_PRICE)
            )
            val fareCalcService = FareCalcService(mockFareDiscountCalcService)

            val result = fareCalcService.calcPrice(route, passengers, departureDate, TripType.ONE_WAY)

            Assertions.assertEquals(Price(ADULT_TOKYO_SHINOSAKA_PRICE), result.amount())
            Assertions.assertFalse(result.isDiscounted())
        }

        @Test
        fun `片道の移動で、割引される場合の運賃合計金額が正しいこと`() {
            val passengers = Passengers(1, 0)
            val route = Route(Station.TOKYO, Station.SHIN_OSAKA)
            val departureDate = DepartureDate.of("2021-12-21")

            every { mockFareDiscountCalcService.calc(allAny(), allAny(), allAny()) }.returns(
                Price(ADULT_TOKYO_SHINOSAKA_PRICE - 1000)
            )
            val fareCalcService = FareCalcService(mockFareDiscountCalcService)

            val result = fareCalcService.calcPrice(route, passengers, departureDate, TripType.ONE_WAY)

            Assertions.assertEquals(Price(ADULT_TOKYO_SHINOSAKA_PRICE - 1000), result.amount())
            Assertions.assertTrue(result.isDiscounted())
        }

        @Test
        fun `往復の移動で割引されない場合の運賃合計金額が正しいこと`() {
            val passengers = Passengers(1, 0)
            val route = Route(Station.TOKYO, Station.SHIN_OSAKA)
            val departureDate = DepartureDate.of("2021-12-21")

            every { mockFareDiscountCalcService.calc(allAny(), allAny(), allAny()) }.returns(
                Price(ADULT_TOKYO_SHINOSAKA_PRICE)
            )
            val fareCalcService = FareCalcService(mockFareDiscountCalcService)

            val result = fareCalcService.calcPrice(route, passengers, departureDate, TripType.ROUND_TRIP)

            Assertions.assertEquals(Price(ADULT_TOKYO_SHINOSAKA_PRICE * 2), result.amount())
            Assertions.assertFalse(result.isDiscounted())
        }

        @Test
        fun `往復の移動で割引される場合の運賃合計金額が正しいこと`() {
            val passengers = Passengers(1, 0)
            val route = Route(Station.TOKYO, Station.SHIN_OSAKA)
            val departureDate = DepartureDate.of("2021-12-21")

            every { mockFareDiscountCalcService.calc(allAny(), allAny(), allAny()) }.returns(
                Price(ADULT_TOKYO_SHINOSAKA_PRICE - 1000)
            )
            val fareCalcService = FareCalcService(mockFareDiscountCalcService)

            val result = fareCalcService.calcPrice(route, passengers, departureDate, TripType.ROUND_TRIP)
            val expected = Price((ADULT_TOKYO_SHINOSAKA_PRICE - 1000) * 2)

            Assertions.assertEquals(expected, result.amount())
            Assertions.assertTrue(result.isDiscounted())
        }

        @Test
        fun `存在しない経路の場合は、例外が発生すること`() {
            val passengers = Passengers(1, 0)
            val route = Route(Station.SHIN_OSAKA, Station.HIMEJI)
            val departureDate = DepartureDate.of("2021-12-21")


            every { mockFareDiscountCalcService.calc(allAny(), allAny(), allAny()) }.returns(Price(9000))
            val fareCalcService = FareCalcService(mockFareDiscountCalcService)

            val target: () -> Unit = {
                fareCalcService.calcPrice(route, passengers, departureDate, TripType.ROUND_TRIP)
            }

            val exception = assertThrows<IllegalArgumentException>(target)
            Assertions.assertEquals("運賃表に存在しない経路です。", exception.message)
        }
    }

}