package domain.model.surcharge

import domain.model.shared.Price
import domain.model.ticket.DepartureDate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate

class SeasonVariationSurchargeTest {
    private val REGULAR_DEPATURE_DATE =  DepartureDate(
        LocalDate.of(LocalDate.now().year + 1, 5, 21)
    )
    private val PEAK_DEPARTURE_DATE = DepartureDate(
        LocalDate.of(LocalDate.now().year + 1, 12,31)
    )
    private val OFF_PEAK_DEPARTURE_DATE = DepartureDate(
        LocalDate.of(LocalDate.now().year + 1, 1,16)
    )

    @Nested
    inner class CalcTest() {
        @Test
        fun `繁忙期中の場合は200円加算された特急料金を取得できること`() {
            val surcharge = Surcharge(Price(2000))

            val result = SeasonalVariationSurcharge.calc(surcharge, departureDate = PEAK_DEPARTURE_DATE)

            val expected = Surcharge(Price(2200))

            Assertions.assertEquals(expected.price(false), result.price(false))
        }

        @Test
        fun `閑散期中の場合は200円割引された特急料金を取得できること`() {
            val surcharge = Surcharge(Price(2000))

            val result = SeasonalVariationSurcharge.calc(surcharge, departureDate = OFF_PEAK_DEPARTURE_DATE)

            val expected = Surcharge(Price(1800))

            Assertions.assertEquals(expected.price(false), result.price(false))
        }

        @Test
        fun `繁忙期・閑散期以外の場合は、引数で渡した特急料金を取得できること`() {
            val surcharge = Surcharge(Price(2000))

            val result = SeasonalVariationSurcharge.calc(surcharge, departureDate = REGULAR_DEPATURE_DATE)

            Assertions.assertEquals(surcharge.price(false), result.price(false))
        }
    }
}