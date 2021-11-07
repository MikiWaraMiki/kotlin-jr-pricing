package jrpricing.demo.first.domain.model.surcharge

import jrpricing.demo.first.domain.model.shared.Price
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class SurchargeTest {

    @Nested
    @DisplayName("子供料金の金額算出テスト")
    inner class PriceTest() {
        @Test
        fun `子供料金の場合は、金額が半額になること`() {
            val surcharge = Surcharge(Price(1000))

            Assertions.assertEquals(Price(500), surcharge.price(true))
        }

        @Test
        fun `5円単位の端数は切り捨てられていること`() {
            val surcharge = Surcharge(Price(1010))

            Assertions.assertEquals(Price(500), surcharge.price(true))
        }
    }
}