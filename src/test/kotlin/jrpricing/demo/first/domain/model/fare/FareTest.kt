package jrpricing.demo.first.domain.model.fare

import jrpricing.demo.first.domain.model.shared.Price
import org.junit.jupiter.api.*

class FareTest {

    @Nested
    @DisplayName("子供料金の金額計算テスト")
    inner class ChildPriceTest() {
        @Test
        fun `半額になること`() {
            val fare = Fare(Price(1000))

            Assertions.assertEquals(Price(500), fare.price(true))
        }

        @Test
        fun `5円の端数が切り捨てられること`() {
            val fare = Fare(Price(8910))

            Assertions.assertEquals(Price(4450), fare.price(true))
        }
    }
}
