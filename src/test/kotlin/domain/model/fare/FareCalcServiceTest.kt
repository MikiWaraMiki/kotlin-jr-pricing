package domain.model.fare

import domain.model.shared.Price
import domain.model.route.Route
import domain.model.station.Station
import domain.model.ticket.DepartureDate
import domain.model.ticket.Ticket
import domain.model.ticket.TicketType
import domain.model.train.SeatType
import domain.model.train.TrainType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDate

class FareCalcServiceTest {
    private val fareCalcService = FareCalcService()
    private val ADULT_TOKYO_SHINOSAKA_PRICE = 8910
    private val CHILD_TOKYO_SHINOSAKA_PRICE = 4450

    @Test
    fun `大人料金の運賃が取得できること`() {
        val ticket = Ticket(
            Route(Station.TOKYO, Station.SHIN_OSAKA),
            DepartureDate(LocalDate.now()),
            TicketType.ONE_WAY,
            TrainType.NOZOMI,
            SeatType.RESERVED,
            isChild = false
        )

        val result = fareCalcService.calcPrice(ticket)

        val expected = Price(ADULT_TOKYO_SHINOSAKA_PRICE)

        Assertions.assertEquals(expected, result)
    }

    @Test
    fun `子供料金の運賃が取得できること`() {
        val ticket = Ticket(
            Route(Station.TOKYO, Station.SHIN_OSAKA),
            DepartureDate(LocalDate.now()),
            TicketType.ONE_WAY,
            TrainType.NOZOMI,
            SeatType.RESERVED,
            isChild = true
        )

        val result = fareCalcService.calcPrice(ticket)

        val expected = Price(CHILD_TOKYO_SHINOSAKA_PRICE)

        Assertions.assertEquals(expected, result)
    }
}