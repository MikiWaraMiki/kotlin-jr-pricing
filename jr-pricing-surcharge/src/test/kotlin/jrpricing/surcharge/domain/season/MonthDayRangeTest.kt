package jrpricing.surcharge.domain.season

import jrpricing.surcharge.domain.exception.DomainException
import jrpricing.surcharge.domain.season.MonthDayRange
import org.junit.jupiter.api.*
import java.time.MonthDay

internal class MonthDayRangeTest {
    @Nested
    @DisplayName("不変条件テスト")
    inner class InitTest() {
        @Test
        fun `開始月日が終了月日よりも後の場合はDomainExceptionが発生すること`() {
            val target: () -> Unit = {
                MonthDayRange(
                    MonthDay.of(12,31),
                    MonthDay.of(1, 1)
                )
            }

            val exception = assertThrows<DomainException>(target)

            Assertions.assertEquals("終了月日を開始月日より後にはできません", exception.message)
        }

        @Test
        fun `開始月日が終了月日よりも前の場合はエラーが発生しないこと`() {
            assertDoesNotThrow {
                MonthDayRange(
                    MonthDay.of(1, 1),
                    MonthDay.of(12,31)
                )
            }
        }
    }

    @Nested
    @DisplayName("containsテスト")
    inner class ContainsTest() {
        @Test
        fun `範囲内であればtrueを返す`() {
            val monthDayRange = MonthDayRange(
                MonthDay.of(12,1),
                MonthDay.of(12, 31)
            )

            Assertions.assertTrue(monthDayRange.contains(MonthDay.of(12,1)))
            Assertions.assertTrue(monthDayRange.contains(MonthDay.of(12,31)))
            Assertions.assertTrue(monthDayRange.contains(MonthDay.of(12,2)))
        }

        @Test
        fun `範囲外であればfalseを返す`() {
            val monthDayRange = MonthDayRange(
                MonthDay.of(12,1),
                MonthDay.of(12, 31)
            )

            Assertions.assertFalse(monthDayRange.contains(MonthDay.of(11,30)))
            Assertions.assertFalse(monthDayRange.contains(MonthDay.of(1,1)))
        }
    }
}