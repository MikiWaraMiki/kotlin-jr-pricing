package jrpricing.catalog.domain.model.shared

import jrpricing.catalog.domain.exception.DomainException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AmountTest {
    @Test
    fun `金額が1円未満の場合はDomainExceptionが発生すること`() {
        val target: () -> Unit = {
            Amount(0)
        }

        val exception = assertThrows<DomainException>(target)

        Assertions.assertEquals("金額は1円以上である必要があります", exception.message)
    }

    @Test
    fun `金額が1円以上の場合はエラーが発生しないこと`() {
        val amount = Amount(1)

        Assertions.assertEquals(Amount(1), amount)
    }
}