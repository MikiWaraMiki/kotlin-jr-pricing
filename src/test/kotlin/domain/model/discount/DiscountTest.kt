package domain.model.discount

import domain.model.discount.rate.DiscountRate
import domain.model.shared.Price
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.math.exp

class DiscountTest {

    @Nested
    @DisplayName("toメソッドのテスト")
    inner class ToTest() {
        @Test
        fun `10%の割引額が取得できること`() {
            val discount = Discount.to(DiscountRate.RATE_10, Price(10000))

            val expected = Price(9000)

            Assertions.assertEquals(expected, discount.price)
        }

        @Test
        fun `10%の割引後金額に１円単位が含まれている場合は切り捨てられること`() {
            val discount = Discount.to(DiscountRate.RATE_10, Price(10010))

            val expected = Price(9000)

            Assertions.assertEquals(expected, discount.price)
        }
    }
}