package lib.domainsupport.valueobject

import org.junit.jupiter.api.*
import java.time.LocalDate

class DateRangeTest {

    @Nested
    @DisplayName("バリデーションテスト")
    inner class ValidationTest() {
        @Test
        fun `開始日を終了日よりも後の日付にすることはできない`() {
            val error = assertThrows<IllegalArgumentException> {
                DateRange(
                    LocalDate.now().plusDays(1),
                    LocalDate.now()
                )
            }

            Assertions.assertEquals("開始日を終了日より後の日付にすることはできません", error.message)
        }

        @Test
        fun `開始日と終了日の範囲を指定ができること`() {
            assertDoesNotThrow {
                DateRange(
                    LocalDate.now(),
                    LocalDate.now().plusDays(1)
                )
            }
        }
    }

    @Nested
    @DisplayName("containsメソッドのテスト")
    inner class ContainsTest() {
        private val dateRange = DateRange(
            LocalDate.now().minusWeeks(1),
            LocalDate.now().plusWeeks(1)
        )

        @Test
        fun `日付範囲に含まれている場合はtrueを返す`() {
            Assertions.assertTrue(dateRange.contains(LocalDate.now()))
        }

        @Test
        fun `開始日よりも前の場合はfalseを返す`() {
            Assertions.assertFalse(
                dateRange.contains(LocalDate.now().minusDays(15))
            )
        }

        @Test
        fun `終了日よりも後の場合はfalseを返す`() {
            Assertions.assertFalse(
                dateRange.contains(LocalDate.now().plusDays(15))
            )
        }
    }
}