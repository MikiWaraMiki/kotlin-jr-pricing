package domain.model.discount.distance

import domain.model.fare.Fare
import domain.model.shared.Passengers
import domain.model.shared.Price
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DistanceDiscountTest {
    @Test
    fun `距離の割引金額が10%割引されていること`() {
        val fare = Fare(Price(10010))
        val passengers = Passengers(1, 0)
        val discount = DistanceDiscount.of(fare, passengers)

        Assertions.assertEquals(Price(9000), discount.afterDiscountedPrice())
    }
}