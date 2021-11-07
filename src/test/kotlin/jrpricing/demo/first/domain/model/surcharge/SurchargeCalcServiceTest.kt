package jrpricing.demo.first.domain.model.surcharge

import jrpricing.demo.first.domain.model.discount.SurchargeDiscountCalcService
import jrpricing.demo.first.domain.model.shared.Price
import jrpricing.demo.first.domain.model.route.Route
import jrpricing.demo.first.domain.model.shared.Passengers
import jrpricing.demo.first.domain.model.station.Station
import jrpricing.demo.first.domain.model.surcharge.testdata.DepartureDateCreator
import jrpricing.demo.first.domain.model.train.SeatType
import jrpricing.demo.first.domain.model.train.TrainType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SurchargeCalcServiceTest {
    private val REGULAR_DEPATURE_DATE =  DepartureDateCreator.REGULAR_DEPATURE_DATE
    private val PEAK_DEPARTURE_DATE = DepartureDateCreator.PEAK_DEPARTURE_DATE
    private val OFF_PEAK_DEPARTURE_DATE = DepartureDateCreator.OFF_PEAK_DEPARTURE_DATE

    private val mockSurchargeDiscountedSurcharge: SurchargeDiscountCalcService = mockk()

    @Test
    fun `大人・指定席・ひかり利用時に割引が適用されない場合の特急料金の結果が正しいこと`() {
        val route = Route(Station.TOKYO, Station.SHIN_OSAKA)
        val trainType = TrainType.HIKARI
        val seatType = SeatType.RESERVED
        val passengers = Passengers(1, 0)

        every { mockSurchargeDiscountedSurcharge.calc(allAny(), allAny()) }.returns(Price(5490))
        val surchargeCalcService = SurchargeCalcService(mockSurchargeDiscountedSurcharge)

        val calcResult = surchargeCalcService.calcPrice(
            route,
            trainType,
            seatType,
            REGULAR_DEPATURE_DATE,
            passengers
        )

        val expectedPrice = Price(5490) // 東京->新大阪

        Assertions.assertEquals(expectedPrice, calcResult.amount())
        Assertions.assertFalse(calcResult.isDiscounted())
    }

    @Test
    fun `子供・指定席・ひかり利用時に割引が適用されない場合の特急料金の結果が正しいこと`() {
        val route = Route(Station.TOKYO, Station.SHIN_OSAKA)
        val trainType = TrainType.HIKARI
        val seatType = SeatType.RESERVED
        val passengers = Passengers(0, 1)

        every { mockSurchargeDiscountedSurcharge.calc(allAny(), allAny()) }.returns(Price(2740))
        val surchargeCalcService = SurchargeCalcService(mockSurchargeDiscountedSurcharge)

        val calcResult = surchargeCalcService.calcPrice(
            route,
            trainType,
            seatType,
            REGULAR_DEPATURE_DATE,
            passengers
        )

        val expectedPrice = Price(2740) // 東京->新大阪

        Assertions.assertEquals(expectedPrice, calcResult.amount())
        Assertions.assertFalse(calcResult.isDiscounted())
    }


    @Test
    fun `自由席利用の場合は530円値引きされた特急料金であること`() {
        val route = Route(Station.TOKYO, Station.SHIN_OSAKA)
        val trainType = TrainType.HIKARI
        val seatType = SeatType.NON_RESERVED
        val passengers = Passengers(1, 0)

        every { mockSurchargeDiscountedSurcharge.calc(allAny(), allAny()) }.returns(Price(5490 - 530))
        val surchargeCalcService = SurchargeCalcService(mockSurchargeDiscountedSurcharge)

        val calcResult = surchargeCalcService.calcPrice(
            route,
            trainType,
            seatType,
            REGULAR_DEPATURE_DATE,
            passengers
        )

        val expectedPrice = Price(5490 - 530) // 東京->新大阪

        Assertions.assertEquals(expectedPrice, calcResult.amount())
        Assertions.assertFalse(calcResult.isDiscounted())
    }

    @Test
    fun `のぞみ利用時の場合は、割増された特急料金であること`() {
        val route = Route(Station.TOKYO, Station.SHIN_OSAKA)
        val trainType = TrainType.NOZOMI
        val seatType = SeatType.RESERVED
        val passengers = Passengers(1, 0)

        every { mockSurchargeDiscountedSurcharge.calc(allAny(), allAny()) }.returns(Price(5490 + 320))
        val surchargeCalcService = SurchargeCalcService(mockSurchargeDiscountedSurcharge)

        val calcResult = surchargeCalcService.calcPrice(
            route,
            trainType,
            seatType,
            REGULAR_DEPATURE_DATE,
            passengers
        )

        val expectedPrice = Price(
            5490 + 320
        )

        Assertions.assertEquals(expectedPrice, calcResult.amount())
        Assertions.assertFalse(calcResult.isDiscounted())
    }


    @Test
    fun `繁忙期に指定席を利用した場合は、200円加算されること`() {
        val route = Route(Station.TOKYO, Station.SHIN_OSAKA)
        val trainType = TrainType.NOZOMI
        val seatType = SeatType.RESERVED
        val passengers = Passengers(1, 0)

        every { mockSurchargeDiscountedSurcharge.calc(allAny(), allAny()) }.returns(Price(5490 + 320 + 200))
        val surchargeCalcService = SurchargeCalcService(mockSurchargeDiscountedSurcharge)


        val calcResult = surchargeCalcService.calcPrice(
            route,
            trainType,
            seatType,
            PEAK_DEPARTURE_DATE,
            passengers
        )

        val expectedPrice = Price(
            5490 + 320 + 200
        )

        Assertions.assertEquals(expectedPrice, calcResult.amount())
        Assertions.assertFalse(calcResult.isDiscounted())
    }

    @Test
    fun `閑散期に指定席を利用した場合は、200円値引きされること`() {
        val route = Route(Station.TOKYO, Station.SHIN_OSAKA)
        val trainType = TrainType.NOZOMI
        val seatType = SeatType.RESERVED
        val passengers = Passengers(1, 0)

        every { mockSurchargeDiscountedSurcharge.calc(allAny(), allAny()) }.returns(Price(5490 + 320 - 200))
        val surchargeCalcService = SurchargeCalcService(mockSurchargeDiscountedSurcharge)


        val calcResult = surchargeCalcService.calcPrice(
            route,
            trainType,
            seatType,
            OFF_PEAK_DEPARTURE_DATE,
            passengers
        )

        val expectedPrice = Price(
            5490 + 320 - 200
        )

        Assertions.assertEquals(expectedPrice, calcResult.amount())
        Assertions.assertFalse(calcResult.isDiscounted())
    }

    @Test
    fun `割引が適用できる場合は、割引後の金額を取得できること`() {
        val route = Route(Station.TOKYO, Station.SHIN_OSAKA)
        val trainType = TrainType.NOZOMI
        val seatType = SeatType.RESERVED
        val passengers = Passengers(50, 0)

        every { mockSurchargeDiscountedSurcharge.calc(allAny(), allAny()) }.returns(Price(5490 * 49))
        val surchargeCalcService = SurchargeCalcService(mockSurchargeDiscountedSurcharge)

        val calcResult = surchargeCalcService.calcPrice(
            route,
            trainType,
            seatType,
            OFF_PEAK_DEPARTURE_DATE,
            passengers
        )

        val expectedPrice = Price(5490 * 49)

        Assertions.assertEquals(expectedPrice, calcResult.amount())
        Assertions.assertTrue(calcResult.isDiscounted())
    }
}