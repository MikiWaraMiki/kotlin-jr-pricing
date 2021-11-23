package jrpricing.surcharge.domain.surcharge

import jrpricing.surcharge.domain.shared.Amount
import jrpricing.surcharge.domain.surcharge.NonReserveSeatSurcharge
import jrpricing.surcharge.domain.surcharge.ReserveSeatSurcharge
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class NonReserveSeatSurchargeTest {
    @Test
    fun `指定席特急料金から530円引かれた金額であること`() {
        val reserveSeatSurcharge = ReserveSeatSurcharge(Amount.of(1000))

        val nonReserveSeatSurcharge = NonReserveSeatSurcharge.from(reserveSeatSurcharge)

        Assertions.assertEquals(Amount.of(1000 - 530), nonReserveSeatSurcharge.amount)
    }
}