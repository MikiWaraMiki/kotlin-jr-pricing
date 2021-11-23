package jrpricing.fare.domain.shared

import jrpricing.fare.domain.exception.DomainException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class DepartureDateTest {
    @Test
    fun `乗車日が今日よりも前の場合はエラーが発生する`() {
        val today = LocalDate.now()

        val yeasterday = today.minusDays(1)

        val target: () -> Unit = {
            DepartureDate(yeasterday)
        }

        val exception = assertThrows<DomainException>(target)

        Assertions.assertEquals("乗車日時が不正な値です", exception.message)
    }

    @Test
    fun `乗車日が今日の場合はエラーが発生しないこと`() {
        val today = LocalDate.now()

        val departureDate = DepartureDate(today)

        Assertions.assertEquals(today, departureDate.value)
    }

    @Test
    fun `乗車日が今日以降の場合はエラーが発生しないこと`() {
        val today = LocalDate.now()

        val tomorrow = today.plusDays(1)

        val departureDate = DepartureDate(tomorrow)

        Assertions.assertEquals(tomorrow, departureDate.value)
    }
}