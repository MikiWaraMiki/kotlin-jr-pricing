package jrpricing.demo.first.domain.model.discount.group

import jrpricing.demo.first.domain.model.shared.Passengers
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class GroupDiscountRuleTest {

    @Nested
    @DisplayName("canメソッドのテスト")
    inner class CanTest() {
        @Test
        fun `乗車人数が8人以上30人以下の場合はtrueを返す`() {
            Assertions.assertTrue(
                GroupDiscountRule(Passengers(4,4)).can()
            )
            Assertions.assertTrue(
                GroupDiscountRule(Passengers(21, 9)).can()
            )
        }

        @Test
        fun `乗車人数が8人未満の場合はfalseを返す`() {
            Assertions.assertFalse(
                GroupDiscountRule(Passengers(4,3)).can()
            )
        }

        @Test
        fun `乗員人数が31人以上の場合はfalseを返す`() {
            Assertions.assertFalse(
                GroupDiscountRule(Passengers(20, 11)).can()
            )
        }
    }
}