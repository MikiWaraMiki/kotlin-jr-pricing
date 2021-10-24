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
   @DisplayName("割引後金額計算")
   inner class AfterDiscountedPriceTest() {
       @Test
       fun `割引金額が少数を含まない結果が正しいこと`() {
           val price = Price(10000)
           val discount = Discount.of(price, DiscountRate.RATE_10)

           val expected = Price(9000)
           Assertions.assertEquals(expected, discount.afterDiscountedPrice())
       }

       @Test
       fun `割引金額が少数を含む場合は切り捨てらていること`() {
           val price = Price(10050)
           val discount = Discount.of(price, DiscountRate.RATE_15)

           val expected = Price(8540) // 10050 * 0.85 = 8542 -> 切り捨てが入って8540
           Assertions.assertEquals(expected, discount.afterDiscountedPrice())
       }
   }
}