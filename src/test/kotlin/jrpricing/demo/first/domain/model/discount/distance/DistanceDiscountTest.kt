package jrpricing.demo.first.domain.model.discount.distance

import jrpricing.demo.first.domain.model.fare.Fare
import jrpricing.demo.first.domain.model.shared.Passengers
import jrpricing.demo.first.domain.model.shared.Price
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class DistanceDiscountTest {

    @Nested
    inner class AfterDiscounted() {
        @Test
        fun `運賃が10%割引されること`() {
            val fare = Fare(Price(10010))

            val distanceDiscount = DistanceDiscount(fare)

            Assertions.assertEquals(Price(9000), distanceDiscount.afterDiscounted())
        }
    }
}