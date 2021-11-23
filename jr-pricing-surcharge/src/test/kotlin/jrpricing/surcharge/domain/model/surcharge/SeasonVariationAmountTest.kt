package jrpricing.surcharge.domain.model.surcharge

import jrpricing.surcharge.domain.model.testdata.TestDepartureMonthDayFactory
import jrpricing.surcharge.domain.shared.Amount
import jrpricing.surcharge.domain.surcharge.ReserveSeatSurcharge
import jrpricing.surcharge.domain.surcharge.Surcharge
import jrpricing.surcharge.domain.surcharge.SeasonVariationAmount
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SeasonVariationAmountTest {
    @Test
    fun `繁忙期に乗車する場合は、指定席特急料金に200円加算された金額が返される`() {
        val surcharge = ReserveSeatSurcharge(Amount.of(1000))
        val departureMonthDay = TestDepartureMonthDayFactory.createPeakDepartureMonthDay()

        val seasonVariationAmount = SeasonVariationAmount(surcharge)

        val result = seasonVariationAmount.amount(departureMonthDay)

        val expected = Amount.of(1000 + 200)

        Assertions.assertEquals(expected, result)
    }

    @Test
    fun `閑散期に乗車する場合は、指定席特急料金から200円減算された金額が返される`() {
        val surcharge = ReserveSeatSurcharge(Amount.of(1000))
        val departureMonthDay = TestDepartureMonthDayFactory.createOffPeakDepartureMonthDay()

        val seasonVariationAmount = SeasonVariationAmount(surcharge)

        val result = seasonVariationAmount.amount(departureMonthDay)

        val expected = Amount.of(1000 - 200)

        Assertions.assertEquals(expected, result)
    }

    @Test
    fun `通常期に乗車する場合は、指定席特急料金の金額がそのまま返される`() {
        val surcharge = ReserveSeatSurcharge(Amount.of(1000))
        val departureMonthDay = TestDepartureMonthDayFactory.createRegularDepartureMonthDay()

        val seasonVariationAmount = SeasonVariationAmount(surcharge)

        val result = seasonVariationAmount.amount(departureMonthDay)

        val expected = Amount.of(1000)

        Assertions.assertEquals(expected, result)
    }
}