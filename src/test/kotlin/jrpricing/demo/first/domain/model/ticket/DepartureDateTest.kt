package jrpricing.demo.first.domain.model.ticket

import org.junit.jupiter.api.*
import java.time.LocalDate

class DepartureDateTest {
    @Nested
    @DisplayName("バリデーションテスト")
    inner class ValidationTest() {
        @Test
        fun `今日よりも前の日付を搭乗にすることはできない`() {
            val yesterday = LocalDate.now().minusDays(1)

            val error = assertThrows<IllegalArgumentException> {
                DepartureDate(yesterday)
            }

            Assertions.assertEquals("本日より前の日付は指定できません", error.message)
        }

        @Test
        fun `今日の日付を搭乗日にすることができる`() {
            assertDoesNotThrow {
                DepartureDate(LocalDate.now())
            }
        }

        @Test
        fun `今日以降の日付を搭乗日にすることができる`() {
            assertDoesNotThrow {
                DepartureDate(LocalDate.now().plusDays(1))
                DepartureDate(LocalDate.now().plusWeeks(1))
                DepartureDate(LocalDate.now().plusMonths(1))
                DepartureDate(LocalDate.now().plusYears(1))
            }
        }
    }
}