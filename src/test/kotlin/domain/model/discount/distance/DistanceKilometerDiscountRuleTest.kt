package domain.model.discount.distance

import domain.model.route.Route
import domain.model.station.Station
import domain.model.ticket.TripType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class DistanceKilometerDiscountRuleTest {
    @Nested
    @DisplayName("isAbleEnabledメソッドのテスト")
    inner class IsAbleEnabledTest() {
        @Test
        fun `片道の場合はfalseを返すこと`() {
            val tripType = TripType.ONE_WAY
            val tripDistance = 601

            val result = DistanceKilometerDiscountRule(tripType, tripDistance).can()

            Assertions.assertFalse(result)
        }

        @Test
        fun `往復の場合、片道の移動距離が601キロ以下の場合は、falseを返すこと`() {
            val tripType = TripType.ROUND_TRIP
            val tripDistance = 600

            val result = DistanceKilometerDiscountRule(tripType, tripDistance).can()

            Assertions.assertFalse(result)
        }

        @Test
        fun `往復の場合、片道の移動距離が601キロ以上の場合は、trueを返すこと`() {
            val tripType = TripType.ROUND_TRIP
            val tripDistance = 601

            val result = DistanceKilometerDiscountRule(tripType, tripDistance).can()

            Assertions.assertTrue(result)
        }
    }
}