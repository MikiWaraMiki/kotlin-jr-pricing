package domain.model.surcharge

import domain.model.shared.Price
import domain.model.route.Route
import domain.model.station.Station
import domain.model.ticket.DepartureDate
import domain.model.ticket.Ticket
import domain.model.ticket.TicketType
import domain.model.train.SeatType
import domain.model.train.TrainType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate

class SurchargeCalcServiceTest {
    private val surchargeCalcService = SurchargeCalcService()
    private val REGULAR_DEPATURE_DATE =  DepartureDate(
        LocalDate.of(LocalDate.now().year + 1, 5, 21)
    )
    private val PEAK_DEPARTURE_DATE = DepartureDate(
        LocalDate.of(LocalDate.now().year + 1, 12,31)
    )
    private val OFF_PEAK_DEPARTURE_DATE = DepartureDate(
        LocalDate.of(LocalDate.now().year + 1, 1,16)
    )

    @Test
    fun `大人・指定席・ひかり利用時の特急料金の結果が正しいこと`() {
        val ticket = Ticket(
            Route(Station.TOKYO, Station.SHIN_OSAKA),
            REGULAR_DEPATURE_DATE,
            TicketType.ONE_WAY,
            TrainType.HIKARI,
            SeatType.RESERVED,
            false
        )

        val result = surchargeCalcService.calcPrice(ticket)

        val expectedPrice = Price(5490) // 東京->新大阪

        Assertions.assertEquals(expectedPrice, result)
    }

    @Test
    fun `子供・指定席・ひかり利用時の特急料金の結果が正しいこと`() {
        val ticket = Ticket(
            Route(Station.TOKYO, Station.SHIN_OSAKA),
            REGULAR_DEPATURE_DATE,
            TicketType.ONE_WAY,
            TrainType.HIKARI,
            SeatType.RESERVED,
            true
        )

        val result = surchargeCalcService.calcPrice(ticket)

        val expectedPrice = Price(2740) // 東京->新大阪

        Assertions.assertEquals(expectedPrice, result)
    }

    @Test
    fun `自由席利用の場合は530円値引きされた特急料金であること`() {
        val ticket = Ticket(
            Route(Station.TOKYO, Station.SHIN_OSAKA),
            REGULAR_DEPATURE_DATE,
            TicketType.ONE_WAY,
            TrainType.HIKARI,
            SeatType.NON_RESERVED,
            false
        )

        val result = surchargeCalcService.calcPrice(ticket)

        val expectedPrice = Price(5490 - 530) // 東京->新大阪

        Assertions.assertEquals(expectedPrice, result)
    }

    @Test
    fun `のぞみ利用時の場合は、割増された特急料金であること`() {
        val ticket = Ticket(
            Route(Station.TOKYO, Station.SHIN_OSAKA),
            REGULAR_DEPATURE_DATE,
            TicketType.ONE_WAY,
            TrainType.NOZOMI,
            SeatType.RESERVED,
            false
        )

        val result = surchargeCalcService.calcPrice(ticket)

        val expectedPrice = Price(
            5490 + 320
        )

        Assertions.assertEquals(expectedPrice, result)
    }

    @Test
    fun `繁忙期に指定席を利用した場合は、200円加算されること`() {
        val ticket = Ticket(
            Route(Station.TOKYO, Station.SHIN_OSAKA),
            PEAK_DEPARTURE_DATE,
            TicketType.ONE_WAY,
            TrainType.NOZOMI,
            SeatType.RESERVED,
            false
        )

        val result = surchargeCalcService.calcPrice(ticket)

        val expectedPrice = Price(
            5490 + 320 + 200
        )

        Assertions.assertEquals(expectedPrice, result)
    }

    @Test
    fun `閑散期に指定席を利用した場合は、200円値引きされること`() {
        val ticket = Ticket(
            Route(Station.TOKYO, Station.SHIN_OSAKA),
            OFF_PEAK_DEPARTURE_DATE,
            TicketType.ONE_WAY,
            TrainType.NOZOMI,
            SeatType.RESERVED,
            false
        )

        val result = surchargeCalcService.calcPrice(ticket)

        val expectedPrice = Price(
            5490 + 320 - 200
        )

        Assertions.assertEquals(expectedPrice, result)

    }
}