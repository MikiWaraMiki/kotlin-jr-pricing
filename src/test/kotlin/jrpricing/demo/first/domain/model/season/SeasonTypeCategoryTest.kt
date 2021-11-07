package jrpricing.demo.first.domain.model.season

import org.junit.jupiter.api.*
import java.time.LocalDate

class SeasonTypeCategoryTest {
    @Nested
    @DisplayName("ofメソッドのテスト")
    inner class OfTest() {

        @Test
        fun `12月25日から12月31日の間であれば年末`() {
            assertAll(
                {
                    val date = LocalDate.parse("2021-12-25")
                    Assertions.assertEquals(SeasonTypeCategory.END_OF_YEAR, SeasonTypeCategory.of(date))
                },
                {
                    val date = LocalDate.parse("2021-12-26")
                    Assertions.assertEquals(SeasonTypeCategory.END_OF_YEAR, SeasonTypeCategory.of(date))
                },
                {
                    val date = LocalDate.parse("2021-12-30")
                    Assertions.assertEquals(SeasonTypeCategory.END_OF_YEAR, SeasonTypeCategory.of(date))
                },

                {
                    val date = LocalDate.parse("2021-12-31")
                    Assertions.assertEquals(SeasonTypeCategory.END_OF_YEAR, SeasonTypeCategory.of(date))
                }
            )
        }

        @Test
        fun `1月1日から1月10日の間であれば年始`() {
            assertAll(
                {
                    val date = LocalDate.parse("2021-01-01")
                    Assertions.assertEquals(SeasonTypeCategory.BEGINNING_OF_YEAR, SeasonTypeCategory.of(date))
                },
                {
                    val date = LocalDate.parse("2021-01-02")
                    Assertions.assertEquals(SeasonTypeCategory.BEGINNING_OF_YEAR, SeasonTypeCategory.of(date))
                },
                {
                    val date = LocalDate.parse("2021-01-09")
                    Assertions.assertEquals(SeasonTypeCategory.BEGINNING_OF_YEAR, SeasonTypeCategory.of(date))
                },
                {
                    val date = LocalDate.parse("2021-01-10")
                    Assertions.assertEquals(SeasonTypeCategory.BEGINNING_OF_YEAR, SeasonTypeCategory.of(date))
                }
            )
        }

        @Test
        fun `1月16日から1月31日の間であれば年始`() {
            assertAll(
                {
                    val date = LocalDate.parse("2021-01-16")
                    Assertions.assertEquals(SeasonTypeCategory.OFF_PEAK, SeasonTypeCategory.of(date))
                },
                {
                    val date = LocalDate.parse("2021-01-17")
                    Assertions.assertEquals(SeasonTypeCategory.OFF_PEAK, SeasonTypeCategory.of(date))
                },
                {
                    val date = LocalDate.parse("2021-01-30")
                    Assertions.assertEquals(SeasonTypeCategory.OFF_PEAK, SeasonTypeCategory.of(date))
                },
                {
                    val date = LocalDate.parse("2021-01-31")
                    Assertions.assertEquals(SeasonTypeCategory.OFF_PEAK, SeasonTypeCategory.of(date))
                }
            )
        }

        @Test
        fun `上記以外は平常`() {
            assertAll(
                {
                    val date = LocalDate.parse("2021-12-24")
                    Assertions.assertEquals(SeasonTypeCategory.REGULAR, SeasonTypeCategory.of(date))
                },
                {
                    val date = LocalDate.parse("2021-01-11")
                    Assertions.assertEquals(SeasonTypeCategory.REGULAR, SeasonTypeCategory.of(date))
                },
                {
                    val date = LocalDate.parse("2021-01-15")
                    Assertions.assertEquals(SeasonTypeCategory.REGULAR, SeasonTypeCategory.of(date))
                },
                {
                    val date = LocalDate.parse("2021-02-01")
                    Assertions.assertEquals(SeasonTypeCategory.REGULAR, SeasonTypeCategory.of(date))
                }
            )
        }
    }
}