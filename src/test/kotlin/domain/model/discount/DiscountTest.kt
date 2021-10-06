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
    @DisplayName("discountPriceのテスト")
    inner class DiscountPriceTest() {
        @Test
        fun `10%の割引の場合の割引額が取得できること`() {
            val discount = Discount(DiscountRate.RATE_10, Price(10000))

            val expected = Price(1000)

            Assertions.assertEquals(expected, discount.discountPrice())
        }

        @Test
        fun `10%の割引金額に１円単位が含まれている場合は切り捨てられること`() {
            val discount = Discount(DiscountRate.RATE_10, Price(10010))

            val expected = Price(1010)

            Assertions.assertEquals(expected, discount.discountPrice())
        }
    }

    @Nested
    @DisplayName("resultメソッドのテスト")
    inner class ResultTest() {
        @Test
        fun `10%の割引後の金額が取得できること`() {
            val discount = Discount(DiscountRate.RATE_10, Price(10000))

            val expected = Price(9000)

            Assertions.assertEquals(expected, discount.result())
        }

        @Test
        fun `10%の割引後金額に１円単位が含まれている場合は切り捨てられること`() {
            val discount = Discount(DiscountRate.RATE_10, Price(10010))

            val expected = Price(9000)

            Assertions.assertEquals(expected, discount.result())
        }
    }
}