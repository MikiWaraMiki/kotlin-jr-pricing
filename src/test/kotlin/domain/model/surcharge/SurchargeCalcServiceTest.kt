package domain.model.surcharge

import domain.model.shared.Price
import domain.model.route.Route
import domain.model.station.Station
import domain.model.surcharge.testdata.DepartureDateCreator
import domain.model.ticket.DepartureDate
import domain.model.train.SeatType
import domain.model.train.TrainType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate

class SurchargeCalcServiceTest {
    private val surchargeCalcService = SurchargeCalcService()
    private val REGULAR_DEPATURE_DATE =  DepartureDateCreator.REGULAR_DEPATURE_DATE
    private val PEAK_DEPARTURE_DATE = DepartureDateCreator.PEAK_DEPARTURE_DATE
    private val OFF_PEAK_DEPARTURE_DATE = DepartureDateCreator.OFF_PEAK_DEPARTURE_DATE

    @Test
    fun `大人・指定席・ひかり利用時の特急料金の結果が正しいこと`() {
        val route = Route(Station.TOKYO, Station.SHIN_OSAKA)
        val trainType = TrainType.HIKARI
        val seatType = SeatType.RESERVED

        val result = surchargeCalcService.calcPrice(
            route,
            trainType,
            seatType,
            REGULAR_DEPATURE_DATE,
            false
        )

        val expectedPrice = Price(5490) // 東京->新大阪

        Assertions.assertEquals(expectedPrice, result)
    }

    @Test
    fun `子供・指定席・ひかり利用時の特急料金の結果が正しいこと`() {
        val route = Route(Station.TOKYO, Station.SHIN_OSAKA)
        val trainType = TrainType.HIKARI
        val seatType = SeatType.RESERVED

        val result = surchargeCalcService.calcPrice(
            route,
            trainType,
            seatType,
            REGULAR_DEPATURE_DATE,
            true
        )

        val expectedPrice = Price(2740) // 東京->新大阪

        Assertions.assertEquals(expectedPrice, result)
    }

    @Test
    fun `自由席利用の場合は530円値引きされた特急料金であること`() {
        val route = Route(Station.TOKYO, Station.SHIN_OSAKA)
        val trainType = TrainType.HIKARI
        val seatType = SeatType.NON_RESERVED

        val result = surchargeCalcService.calcPrice(
            route,
            trainType,
            seatType,
            REGULAR_DEPATURE_DATE,
            false
        )

        val expectedPrice = Price(5490 - 530) // 東京->新大阪

        Assertions.assertEquals(expectedPrice, result)
    }

    @Test
    fun `のぞみ利用時の場合は、割増された特急料金であること`() {
        val route = Route(Station.TOKYO, Station.SHIN_OSAKA)
        val trainType = TrainType.NOZOMI
        val seatType = SeatType.RESERVED

        val result = surchargeCalcService.calcPrice(
            route,
            trainType,
            seatType,
            REGULAR_DEPATURE_DATE,
            false
        )

        val expectedPrice = Price(
            5490 + 320
        )

        Assertions.assertEquals(expectedPrice, result)
    }

    @Test
    fun `繁忙期に指定席を利用した場合は、200円加算されること`() {
        val route = Route(Station.TOKYO, Station.SHIN_OSAKA)
        val trainType = TrainType.NOZOMI
        val seatType = SeatType.RESERVED

        val result = surchargeCalcService.calcPrice(
            route,
            trainType,
            seatType,
            PEAK_DEPARTURE_DATE,
            false
        )

        val expectedPrice = Price(
            5490 + 320 + 200
        )

        Assertions.assertEquals(expectedPrice, result)
    }

    @Test
    fun `閑散期に指定席を利用した場合は、200円値引きされること`() {
        val route = Route(Station.TOKYO, Station.SHIN_OSAKA)
        val trainType = TrainType.NOZOMI
        val seatType = SeatType.RESERVED

        val result = surchargeCalcService.calcPrice(
            route,
            trainType,
            seatType,
            OFF_PEAK_DEPARTURE_DATE,
            false
        )

        val expectedPrice = Price(
            5490 + 320 - 200
        )

        Assertions.assertEquals(expectedPrice, result)

    }
}