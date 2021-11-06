package domain.model.discount

import domain.model.discount.distance.DistanceKilometerDiscountRule
import domain.model.discount.group.GroupDiscountRule
import domain.model.discount.largegroup.LargeGroupDiscountRule
import domain.model.fare.Fare
import domain.model.route.Route
import domain.model.shared.Passengers
import domain.model.shared.Price
import domain.model.station.Station
import domain.model.ticket.DepartureDate
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate

class FareDiscountCalcServiceTest {
    private val mockDistanceDiscountRule: DistanceKilometerDiscountRule = mockk()
    private val mockGroupDiscountRule: GroupDiscountRule = mockk()
    private val mockLargeGroupDiscountRule: LargeGroupDiscountRule = mockk()

    private val RATE10_DEPARTURE_DATE = DepartureDate(LocalDate.parse("2021-12-21"))
    private val RATE15_DEPARTURE_DATE = DepartureDate(LocalDate.parse("2021-12-20"))

    @Test
    fun `距離割引のみが適用できる場合は、10%割引きされた金額であること`() {
        val fare = Fare(Price(10010))
        val passengers = Passengers(1, 0)

        val fareDiscountCalcService = FareDiscountCalcService(
            mockDistanceDiscountRule,
            mockGroupDiscountRule,
            mockLargeGroupDiscountRule
        )

        every { mockDistanceDiscountRule.can() }.returns(true)
        every { mockGroupDiscountRule.can() }.returns(false)
        every { mockLargeGroupDiscountRule.can() }.returns(false)

        val resultPrice = fareDiscountCalcService.calc(fare, passengers, RATE10_DEPARTURE_DATE)

        // See domain.model.discount.distance.DistanceDiscountTest
        val expectedPrice = Price(9000)

        Assertions.assertEquals(expectedPrice, resultPrice)
    }

    @Test
    fun `10%割引期間で通常団体割引のみ利用できる場合は、10%割引された金額であること`() {
        val fare = Fare(Price(10010))
        val passengers = Passengers(1, 0)

        val fareDiscountCalcService = FareDiscountCalcService(
            mockDistanceDiscountRule,
            mockGroupDiscountRule,
            mockLargeGroupDiscountRule
        )
        every { mockDistanceDiscountRule.can() }.returns(false)
        every { mockGroupDiscountRule.can() }.returns(true)
        every { mockLargeGroupDiscountRule.can() }.returns(false)

        val resultPrice = fareDiscountCalcService.calc(fare, passengers, RATE10_DEPARTURE_DATE)

        val expectedPrice = Price(9000)

        Assertions.assertEquals(expectedPrice, resultPrice)
    }

    @Test
    fun `15%割引期間で通常団体割引のみ利用できる場合は、15%割引された金額であること`() {
        val fare = Fare(Price(10010))
        val passengers = Passengers(1, 0)

        val fareDiscountCalcService = FareDiscountCalcService(
            mockDistanceDiscountRule,
            mockGroupDiscountRule,
            mockLargeGroupDiscountRule
        )
        every { mockDistanceDiscountRule.can() }.returns(false)
        every { mockGroupDiscountRule.can() }.returns(true)
        every { mockLargeGroupDiscountRule.can() }.returns(false)

        val resultPrice = fareDiscountCalcService.calc(fare, passengers, RATE15_DEPARTURE_DATE)

        val expectedPrice = Price(8500)

        Assertions.assertEquals(expectedPrice, resultPrice)
    }

    @Test
    fun `特別団体割引のみ利用できる場合は、無賃扱人員分の金額が割り引かれた金額であること`() {
        val fare = Fare(Price(10010))
        val passengers = Passengers(1, 0)

    }
}