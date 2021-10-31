package domain.model.discount.group

import domain.model.discount.group.GroupDiscountRule
import domain.model.shared.Passengers
import domain.model.ticket.DepartureDate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate

class GroupDiscountRuleTest {

    @Nested
    @DisplayName("canメソッドのテスト")
    inner class CanTest() {
        @Test
        fun `乗車人数が8人以上の場合はtrueを返す`() {
            Assertions.assertTrue(
                GroupDiscountRule(
                    Passengers(4,5),
                    DepartureDate(LocalDate.now())
                ).can()
            )
            Assertions.assertTrue(
                GroupDiscountRule(
                    Passengers(4,4),
                    DepartureDate(LocalDate.now())

                ).can()
            )
        }

        @Test
        fun `乗車人数が8人未満の場合はfalseを返す`() {
            Assertions.assertFalse(
                GroupDiscountRule(
                    Passengers(4,3),
                    DepartureDate(LocalDate.now())
                ).can()
            )
        }
    }
}