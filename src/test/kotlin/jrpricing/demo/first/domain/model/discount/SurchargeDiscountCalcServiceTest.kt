package jrpricing.demo.first.domain.model.discount

import jrpricing.demo.first.domain.model.discount.largegroup.LargeGroupDiscountRule
import jrpricing.demo.first.domain.model.shared.Passengers
import jrpricing.demo.first.domain.model.shared.Price
import jrpricing.demo.first.domain.model.surcharge.Surcharge
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class SurchargeDiscountCalcServiceTest {

    @Nested
    inner class CalcTest() {
        private val mockLargeGroupDiscountRule: LargeGroupDiscountRule = mockk()

        @Test
        fun `特別団体割引が適用される場合は、割引された合計金額を取得できること`() {
            val surcharge = Surcharge(Price(10010))
            val passengers = Passengers(49, 1)

            every { mockLargeGroupDiscountRule.can() }.returns(true)
            val calcService = SurchargeDiscountCalcService(mockLargeGroupDiscountRule)

            val result = calcService.calc(surcharge, passengers)

            val expected = Price(
                surcharge.price(false).value * 48 + surcharge.price(true).value * 1
            )

            Assertions.assertEquals(expected, result)
        }

        @Test
        fun `特別団体割引が適用されない場合は、割引されていない合計金額を取得できること`() {
            val surcharge = Surcharge(Price(10010))
            val passengers = Passengers(4, 1)

            every { mockLargeGroupDiscountRule.can() }.returns(false)
            val calcService = SurchargeDiscountCalcService(mockLargeGroupDiscountRule)

            val result = calcService.calc(surcharge, passengers)

            val expected = Price(
                surcharge.price(false).value * 4 + surcharge.price(true).value * 1
            )
        }
    }
}