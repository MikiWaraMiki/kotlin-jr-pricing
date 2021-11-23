package jrpricing.surcharge.domain.surcharge

import jrpricing.surcharge.domain.testdata.TestDepartureMonthDayFactory
import jrpricing.surcharge.domain.shared.Amount
import jrpricing.surcharge.domain.shared.SeatType
import jrpricing.surcharge.domain.surcharge.ReserveSeatSurcharge
import jrpricing.surcharge.domain.surcharge.SurchargeCalculator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SurchargeCalculatorTest {
    @Test
    fun `自由席を利用している場合は、指定席料金から530円引かれた金額が特急料金になること`() {
        val reserveSeatSurcharge = ReserveSeatSurcharge(Amount.of(10000))
        val departureMonthDay = TestDepartureMonthDayFactory.createRegularDepartureMonthDay()
        val seatType = SeatType.NON_RESERVED

        val calculator = SurchargeCalculator()

        val surcharge = calculator.calc(reserveSeatSurcharge, departureMonthDay, seatType)

        val expected = Amount.of(9470)

        Assertions.assertEquals(expected, surcharge.amount)
    }

    @Test
    fun `指定席利用で、乗車日が繁忙期の場合は、指定席料金から200円加算された金額が特急料金になること`() {
        val reserveSeatSurcharge = ReserveSeatSurcharge(Amount.of(10000))
        val departureMonthDay = TestDepartureMonthDayFactory.createPeakDepartureMonthDay()
        val seatType = SeatType.RESERVED

        val calculator = SurchargeCalculator()

        val surcharge = calculator.calc(reserveSeatSurcharge, departureMonthDay, seatType)

        val expected = Amount.of(10200)

        Assertions.assertEquals(expected, surcharge.amount)
    }

    @Test
    fun `指定席利用で、乗車日が閑散期の場合は、指定席料金から200円引かれた金額が特急料金になること`() {
        val reserveSeatSurcharge = ReserveSeatSurcharge(Amount.of(10000))
        val departureMonthDay = TestDepartureMonthDayFactory.createOffPeakDepartureMonthDay()
        val seatType = SeatType.RESERVED

        val calculator = SurchargeCalculator()

        val surcharge = calculator.calc(reserveSeatSurcharge, departureMonthDay, seatType)

        val expected = Amount.of(9800)

        Assertions.assertEquals(expected, surcharge.amount)
    }

    @Test
    fun `指定席利用で、乗車日が通常期の場合は、指定席料金が特急料金になること`() {
        val reserveSeatSurcharge = ReserveSeatSurcharge(Amount.of(10000))
        val departureMonthDay = TestDepartureMonthDayFactory.createRegularDepartureMonthDay()
        val seatType = SeatType.RESERVED

        val calculator = SurchargeCalculator()

        val surcharge = calculator.calc(reserveSeatSurcharge, departureMonthDay, seatType)

        val expected = Amount.of(10000)

        Assertions.assertEquals(expected, surcharge.amount)

    }
}