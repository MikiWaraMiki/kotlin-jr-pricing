package domain.model.discount.distance

import domain.model.shared.Price
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DistanceDiscountTest {
    @Test
    fun `距離の割引金額が10%割引されていること`() {
        val basePrice = Price(10000)
        val discount = DistanceDiscount(basePrice)

        Assertions.assertEquals(Price(9000), discount.afterDiscountedPrice())
    }
}