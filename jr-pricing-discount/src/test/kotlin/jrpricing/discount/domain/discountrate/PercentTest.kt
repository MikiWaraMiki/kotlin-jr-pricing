package jrpricing.discount.domain.discountrate

import jrpricing.discount.domain.discountrate.Percent
import jrpricing.discount.domain.exception.DomainException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class PercentTest {
    @Test
    fun `割引率が0未満の場合はエラーが発生すること`() {
        val target: () -> Unit = {
            Percent(-1)
        }

        val exception = assertThrows<DomainException>(target)

        Assertions.assertEquals("割引率が0以上の数値です", exception.message)
    }

    @Test
    fun `割引率が0以上の場合はエラーが発生しないこと`() {
        val target: () -> Unit = {
            Percent(0)
        }

        assertDoesNotThrow(target)
    }
}