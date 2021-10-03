package lib.domainsupport.valueobject

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
        private val monthDayRange = MonthDayRange(
            MonthDay.of(12, 1),
            MonthDay.of(1,10)
        )

        @Test
        fun `開始月日よりも前であればfalseを返す`() {
            Assertions.assertFalse(
                monthDayRange.contains(MonthDay.of(11,30))
            )
        }

        @Test
        fun `終了月日よりも後であればfalseを返す`() {
            Assertions.assertFalse(
                monthDayRange.contains(MonthDay.of(1,11))
            )
        }

        @Test
        fun `範囲内に含まれている場合はtrueを返す`() {
            Assertions.assertTrue(
                monthDayRange.contains(MonthDay.of(12,1))
            )
            Assertions.assertTrue(
                monthDayRange.contains(MonthDay.of(1,10))
            )
            Assertions.assertTrue(
                monthDayRange.contains(MonthDay.of(12,2))
            )
            Assertions.assertTrue(
                monthDayRange.contains(MonthDay.of(1,9))
            )
        }
    }
}