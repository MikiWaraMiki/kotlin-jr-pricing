package jrpricing.discount.domain.shared

import jrpricing.discount.domain.discountrate.Percent
import jrpricing.discount.domain.exception.DomainException
import org.junit.jupiter.api.*

internal class AmountTest {
    @Nested
    @DisplayName("不変条件テスト")
    inner class InitializeTest() {
        @Test
        fun `金額が1円未満の場合はエラーが発生すること`() {
            val target: () -> Unit = {
                Amount.of(0)
            }

            val exception = assertThrows<DomainException>(target)

            Assertions.assertEquals("金額は1円以上である必要があります", exception.message)
        }

        @Test
        fun `金額が1円以上の場合はエラーが発生すること`() {
            val target: () -> Unit = {
                Amount.of(1)
            }

            assertDoesNotThrow(target)
        }
    }

    @Nested
    inner class WithAdjustTest() {
        @Test
        fun `金額に1円単位が含まれている場合は切り捨てられた金額を取得できること`() {
            val amount = Amount.withAdjust(11)

            Assertions.assertEquals(Amount.of(10), amount)
        }

        @Test
        fun `金額に5円単位が含まれている場合は切り捨てられた金額を取得できること`() {
            val amount = Amount.withAdjust(15)

            Assertions.assertEquals(Amount.of(10), amount)
        }

        @Test
        fun `金額に1円単位・5円単位が含まれている場合は切り捨てられないこと`() {
            val amount = Amount.withAdjust(1010)

            Assertions.assertEquals(Amount.of(1010), amount)
        }
    }

    @Nested
    inner class PercentOfTest() {
        @Test
        fun `指定された割合の金額を算出できること`() {
            val percent = Percent(10)

            val amount = Amount.of(1000)

            val result = amount.percentOf(percent)

            Assertions.assertEquals(Amount.of(100), result)
        }

        @Test
        fun `指定された割合の金額に1円単位が含まれる場合は切り捨てられること`() {
            val percent = Percent(90)

            val amount = Amount.of(10010)

            val result = amount.percentOf(percent)

            Assertions.assertEquals(Amount.of(9000), result)
        }
    }
}