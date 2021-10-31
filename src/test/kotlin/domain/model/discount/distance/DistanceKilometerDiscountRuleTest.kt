package domain.model.discount.distance

import domain.model.discount.distance.DistanceKilometerDiscountRule
import domain.model.route.Route
import domain.model.station.Station
import domain.model.ticket.TicketType
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
            val ticketType = TicketType.ONE_WAY
            val route = Route(Station.TOKYO, Station.HIMEJI)

            val result = DistanceKilometerDiscountRule(ticketType, route).can()

            Assertions.assertFalse(result)
        }

        @Test
        fun `往復の場合、片道の移動距離が601キロ以下の場合は、falseを返すこと`() {
            val ticketType = TicketType.ROUND_TRIP
            val route = Route(Station.TOKYO, Station.SHIN_OSAKA)

            val result = DistanceKilometerDiscountRule(ticketType, route).can()

            Assertions.assertFalse(result)
        }

        @Test
        fun `往復の場合、片道の移動距離が601キロ以上の場合は、trueを返すこと`() {
            val ticketType = TicketType.ROUND_TRIP
            val route = Route(Station.TOKYO, Station.HIMEJI)

            val result = DistanceKilometerDiscountRule(ticketType, route).can()

            Assertions.assertTrue(result)
        }
    }
}