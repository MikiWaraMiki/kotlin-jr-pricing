package jrpricing.demo.first.domain.model.discount.group

import jrpricing.demo.first.domain.model.discount.DiscountRate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate

class GroupDiscountRateCategoryTest {

    @Nested
    @DisplayName("rateFromDate")
    inner class RateFromDateTest() {

        @Test
        fun `乗車日が12月21日の場合は割引率10%を返す`() {
            val rate = GroupDiscountRateCategory.rateFromDate(LocalDate.parse("2021-12-21"))

            Assertions.assertEquals(DiscountRate.RATE_10, rate)
        }

        @Test
        fun `乗車日が1月10日の場合は割引率10%を返す`() {
            val rate = GroupDiscountRateCategory.rateFromDate(LocalDate.parse("2021-01-10"))

            Assertions.assertEquals(DiscountRate.RATE_10, rate)
        }

        @Test
        fun `乗車日が12月21日から1月10日の間であれば割引率10%を返す`() {
            val rate = GroupDiscountRateCategory.rateFromDate(LocalDate.parse("2022-12-22"))

            Assertions.assertEquals(DiscountRate.RATE_10, rate)
        }

        @Test
        fun `乗車日が12月21日から1月10日の範囲外であれば割引率15%を返す`() {
            val rate = GroupDiscountRateCategory.rateFromDate(LocalDate.parse("2021-12-20"))

            Assertions.assertEquals(DiscountRate.RATE_15, rate)
        }
    }
}