package jrpricing.demo.first.domain.model.shared

import jrpricing.demo.first.domain.model.exception.DomainException
import org.junit.jupiter.api.*

class PriceTest {

    @Nested
    @DisplayName("バリデーションテスト")
    inner class ValidationTest() {
        @Test
        fun `金額が1円未満の場合はエラーになる`() {
            val target: () -> Unit = { Price(0) }

            val exception = assertThrows<DomainException>(target)

            Assertions.assertEquals("金額は1円以上である必要があります", exception.message)
        }

        @Test
        fun `金額に5円が利用されている場合はエラーになる`() {
            val target: () -> Unit = { Price(5) }

            val exception = assertThrows<DomainException>(target)
            Assertions.assertEquals("金額は10円単位である必要があります", exception.message)
        }

        @Test
        fun `金額に1円が利用されている場合はエラーになる`() {
            val target: () -> Unit = { Price(1) }

            val exception = assertThrows<DomainException>(target)
            Assertions.assertEquals("金額は10円単位である必要があります", exception.message)
        }
    }

    @Nested
    @DisplayName("toメソッドのテスト")
    inner class ToTest() {
        @Test
        fun `金額に5円が使われている場合は切り捨てられた金額を取得できる`() {
            val price = Price.of(1015)

            Assertions.assertEquals(1010, price.value)
        }

        @Test
        fun `金額に1円が使われている場合は切り捨てられた金額を取得できる`() {
            val price = Price.of(1009)

            Assertions.assertEquals(1000, price.value)
        }

        @Test
        fun `金額に1円,5円が利用されていなければそのままの金額を取得できる`() {
            val price = Price.of(1510)

            Assertions.assertEquals(1510, price.value)
        }
    }
}