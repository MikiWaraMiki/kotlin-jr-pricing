package jrpricing.fare.domain.model.shared

import jrpricing.fare.domain.model.exception.DomainException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AmountTest {
    @Test
    fun `金額が1円未満である場合はDomainExceptionが発生すること`() {
        val target: () -> Unit = {
            Amount.of(0)
        }

        val exception = assertThrows<DomainException>(target)

        Assertions.assertEquals("金額は1円以上である必要があります", exception.message)
    }

    @Test
    fun `金額が1円以上の場合はDomainExceptionが発生しないこと`() {
        val amount = Amount.of(1)

        Assertions.assertEquals(1, amount.value)
    }
}