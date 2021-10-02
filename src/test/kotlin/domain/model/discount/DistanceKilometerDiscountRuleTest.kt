package domain.model.discount

import domain.model.discount.rule.DistanceKilometerDiscountRule
import domain.model.route.Route
import domain.model.station.Station
import domain.model.ticket.DepartureDate
import domain.model.ticket.Ticket
import domain.model.ticket.TicketType
import domain.model.train.SeatType
import domain.model.train.TrainType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate

class DistanceKilometerDiscountRuleTest {
    @Nested
    @DisplayName("isAbleEnabledメソッドのテスト")
    inner class IsAbleEnabledTest() {
        @Test
        fun `片道の場合はfalseを返すこと`() {
            val ticket = Ticket(
                route = Route(Station.TOKYO, Station.HIMEJI),
                departureDate = DepartureDate(LocalDate.now()),
                ticketType = TicketType.ONE_WAY,
                trainType =  TrainType.HIKARI,
                seatType = SeatType.NON_RESERVED,
                isChild = false
            )

            val result = DistanceKilometerDiscountRule(ticket).can()

            Assertions.assertFalse(result)
        }

        @Test
        fun `往復の場合、片道の移動距離が601キロ以下の場合は、falseを返すこと`() {
            val ticket = Ticket(
                route = Route(Station.TOKYO, Station.SHIN_OSAKA),
                departureDate = DepartureDate(LocalDate.now()),
                ticketType = TicketType.ROUND_TRIP,
                trainType = TrainType.HIKARI,
                seatType = SeatType.NON_RESERVED,
                isChild = false
            )

            val result = DistanceKilometerDiscountRule(ticket).can()

            Assertions.assertFalse(result)
        }

        @Test
        fun `往復の場合、片道の移動距離が601キロ以上の場合は、trueを返すこと`() {
            val ticket = Ticket(
                route = Route(Station.TOKYO, Station.HIMEJI),
                departureDate = DepartureDate(LocalDate.now()),
                ticketType = TicketType.ROUND_TRIP,
                trainType = TrainType.HIKARI,
                seatType = SeatType.NON_RESERVED,
                isChild = false
            )

            val result = DistanceKilometerDiscountRule(ticket).can()

            Assertions.assertTrue(result)
        }
    }
}