package domain.model.discount.largegroup

import domain.model.shared.Passengers
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LargeGroupDiscountRuleTest {
    @Test
    fun `乗員人数が31人以上の場合はtrueを返す`() {
        Assertions.assertTrue(
            LargeGroupDiscountRule(Passengers(20,11)).can()
        )
    }

    @Test
    fun `乗員人数が31人未満の場合はfalseを返す`() {
        Assertions.assertFalse(
            LargeGroupDiscountRule(Passengers(20,10)).can()
        )
    }
}