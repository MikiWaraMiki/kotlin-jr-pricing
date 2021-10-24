package domain.model.discount

import domain.model.discount.rate.DiscountRate
import domain.model.purcharse.Passengers
import domain.model.route.Route
import domain.model.shared.Price
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

class DiscountCalcServiceTest {
    private val LONGWAY_ROUTE = Route(Station.TOKYO, Station.HIMEJI)
}