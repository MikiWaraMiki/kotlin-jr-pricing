package domain.model.fare

import org.junit.jupiter.api.*

class FareTest {

    @Test
    @DisplayName("運賃に5円単位が含まれている場合はエラーが発生すること")
    fun throwErrorWhenFareIsIncludeFiveYen() {
        val error = assertThrows<IllegalArgumentException> {
            Fare(2005)
        }

        Assertions.assertEquals("運賃は10円単位である必要があります", error.message)
    }

    @Test
    @DisplayName("運賃に1円単位が含まれている場合はエラーが発生すること")
    fun throwErrorWhenFareIsIncludeOneYen() {
        val error = assertThrows<IllegalArgumentException> {
            Fare(2011)
        }

        Assertions.assertEquals("運賃は10円単位である必要があります", error.message)
    }

    @Test
    @DisplayName("運賃が10円単位である場合はエラーが発生しないこと")
    fun notThrowErrorWhenFareDivisibleTenYen() {
        assertDoesNotThrow {
            Fare(2010)
        }
    }
}