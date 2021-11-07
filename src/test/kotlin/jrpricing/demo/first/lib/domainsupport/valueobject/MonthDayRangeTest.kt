package jrpricing.demo.first.lib.domainsupport.valueobject

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.MonthDay

class MonthDayRangeTest {

    @Nested
    @DisplayName("containsメソッドのテスト")
    inner class ContainsTest() {
        private val differentMonthDayRange = MonthDayRange(
            MonthDay.of(12, 1),
            MonthDay.of(1,10)
        )
        private val sameMonthDayRange = MonthDayRange(
            MonthDay.of(12,2),
            MonthDay.of(12,14)
        )

        @Test
        fun `開始月日よりも前であればfalseを返す`() {
            Assertions.assertFalse(
                differentMonthDayRange.contains(MonthDay.of(11,30))
            )
            Assertions.assertFalse(
                sameMonthDayRange.contains(MonthDay.of(12,1))
            )
        }

        @Test
        fun `終了月日よりも後であればfalseを返す`() {
            Assertions.assertFalse(
                differentMonthDayRange.contains(MonthDay.of(1,11))
            )
            Assertions.assertFalse(
                sameMonthDayRange.contains(MonthDay.of(12,15))
            )
        }

        @Test
        fun `範囲内に含まれている場合はtrueを返す`() {
            Assertions.assertTrue(
                differentMonthDayRange.contains(MonthDay.of(12,1))
            )
            Assertions.assertTrue(
                differentMonthDayRange.contains(MonthDay.of(1,10))
            )
            Assertions.assertTrue(
                differentMonthDayRange.contains(MonthDay.of(12,2))
            )
            Assertions.assertTrue(
                differentMonthDayRange.contains(MonthDay.of(1,9))
            )

            Assertions.assertTrue(
                sameMonthDayRange.contains(MonthDay.of(12,2))
            )
            Assertions.assertTrue(
                sameMonthDayRange.contains(MonthDay.of(12,3))
            )
            Assertions.assertTrue(
                sameMonthDayRange.contains(MonthDay.of(12,13))
            )
            Assertions.assertTrue(
                sameMonthDayRange.contains(MonthDay.of(12,14))
            )
        }
    }
}