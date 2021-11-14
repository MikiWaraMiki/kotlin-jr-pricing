package jrpricing.demo.first.domain.model.surcharge

import jrpricing.demo.first.domain.model.shared.Price
import jrpricing.demo.first.domain.model.surcharge.testdata.DepartureDateCreator
import jrpricing.demo.first.domain.model.train.SeatType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class SeatTypeSurchargeTest {
    @Nested
    inner class CalcTest() {

        @Test
        fun `自由席利用の場合は530円減額された特急料金を取得できること`() {
            val surcharge = Surcharge(Price(2000))
            val departureDate = DepartureDateCreator.PEAK_DEPARTURE_DATE

            val result = SeatTypeSurcharge.calc(surcharge, SeatType.NON_RESERVED, departureDate)

            val expected = Price(2000 - 530)

            Assertions.assertEquals(expected, result.price(false))
        }

        // TODO: SeasonalVariationSurchargeでテストしているので不要かも
        @Test
        fun `繁忙期に指定席を利用する場合は、200円加算された特急料金を取得できること`() {
            val surcharge = Surcharge(Price(2000))
            val departureDate = DepartureDateCreator.PEAK_DEPARTURE_DATE

            val result = SeatTypeSurcharge.calc(surcharge, SeatType.RESERVED, departureDate)

            val expected = Price(2000 + 200)

            Assertions.assertEquals(expected, result.price(false))
        }

        @Test
        fun `閑散期に指定席を利用する場合は、200円減額された特急料金を取得できること`() {
            val surcharge = Surcharge(Price(2000))
            val departureDate = DepartureDateCreator.OFF_PEAK_DEPARTURE_DATE

            val result = SeatTypeSurcharge.calc(surcharge, SeatType.RESERVED, departureDate)

            val expected = Price(2000 - 200)

            Assertions.assertEquals(expected, result.price(false))
        }

        @Test
        fun `繁忙期・閑散期以外に指定席を利用する場合は、特急料金が変動しないこと`() {
            val surcharge = Surcharge(Price(2000))

            val departureDate = DepartureDateCreator.REGULAR_DEPATURE_DATE

            val result = SeatTypeSurcharge.calc(surcharge, SeatType.RESERVED, departureDate)

            Assertions.assertEquals(surcharge.price(false), result.price(false))
        }
    }
}