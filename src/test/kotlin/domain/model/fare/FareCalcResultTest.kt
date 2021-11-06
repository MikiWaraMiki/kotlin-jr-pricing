package domain.model.fare

import domain.model.shared.Passengers
import domain.model.shared.Price
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class FareCalcResultTest {

    @Nested
    inner class TotalTest() {

        @Test
        fun `大人1人の合計金額が正しいこと`() {
            val passengers = Passengers(1, 0)
            val fare = Fare(Price(10050))

            val fareCalcResult = FareCalcResult(fare, passengers)

            Assertions.assertEquals(Price(10050), fareCalcResult.total())
        }

        @Test
        fun `子供1人の合計金額が正しいこと`() {
            val passengers = Passengers(0, 1)
            val fare = Fare(Price(10050))

            val fareCalcResult = FareCalcResult(fare, passengers)

            Assertions.assertEquals(Price(5020), fareCalcResult.total())
        }

        @Test
        fun `大人1人と子供1人の合計金額が正しいこと`() {
            val passengers = Passengers(1, 1)
            val fare = Fare(Price(10050))

            val fareCalcResult = FareCalcResult(fare, passengers)

            Assertions.assertEquals(Price(15070), fareCalcResult.total())
        }
    }
}