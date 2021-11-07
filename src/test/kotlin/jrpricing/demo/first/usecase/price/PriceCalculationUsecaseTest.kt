package jrpricing.demo.first.usecase.price

import io.mockk.every
import io.mockk.mockk
import jrpricing.demo.first.domain.model.exception.DomainException
import jrpricing.demo.first.domain.model.fare.*
import jrpricing.demo.first.domain.model.shared.Passengers
import jrpricing.demo.first.domain.model.shared.Price
import jrpricing.demo.first.domain.model.surcharge.*
import jrpricing.demo.first.domain.model.surcharge.testdata.DepartureDateCreator
import jrpricing.demo.first.domain.model.ticket.TripType
import jrpricing.demo.first.usecase.price.testdata.UsecaseInputCreator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PriceCalculationUsecaseTest {
    private val fareCalcService: FareCalcService = mockk()
    private val surchargeCalcService: SurchargeCalcService = mockk()

    @Test
    fun `存在しない出発駅を指定した場合はエラーが発生する`() {
        val input = UsecaseInputCreator.create(
            departureStationName = "miyagi"
        )

        val priceCalculationUsecase = PriceCalculationUsecase(fareCalcService, surchargeCalcService)

        val target: () -> Unit = {
            priceCalculationUsecase.calc(
                input.departureStationName,
                input.arrivalStationName,
                input.tripType,
                input.trainType,
                input.seatType,
                input.passengers,
                input.departureDate
            )
        }

        val exception = assertThrows<DomainException>(target)

        Assertions.assertEquals("存在しない駅です", exception.message)
    }

    @Test
    fun `存在しない到着駅を指定した場合はエラーが発生する`() {
        val input = UsecaseInputCreator.create(
            arrivalStationName = "miyagi"
        )

        val priceCalculationUsecase = PriceCalculationUsecase(fareCalcService, surchargeCalcService)

        val target: () -> Unit = {
            priceCalculationUsecase.calc(
                input.departureStationName,
                input.arrivalStationName,
                input.tripType,
                input.trainType,
                input.seatType,
                input.passengers,
                input.departureDate
            )
        }

        val exception = assertThrows<DomainException>(target)

        Assertions.assertEquals("存在しない駅です", exception.message)
    }

    @Test
    fun `出発駅と到着駅の経路が存在しない場合はエラーが発生する`() {
        val input = UsecaseInputCreator.create(
            departureStationName = "himeji",
            arrivalStationName = "tokyo"
        )

        val priceCalculationUsecase = PriceCalculationUsecase(fareCalcService, surchargeCalcService)

        val target: () -> Unit = {
            priceCalculationUsecase.calc(
                input.departureStationName,
                input.arrivalStationName,
                input.tripType,
                input.trainType,
                input.seatType,
                input.passengers,
                input.departureDate
            )
        }

        val exception = assertThrows<DomainException>(target)

        Assertions.assertEquals("経路表に登録されていない出発駅です", exception.message)
    }

    @Test
    fun `割引されない場合の片道料金の金額が取得できること`() {
        val input = UsecaseInputCreator.create(
            passengers = Passengers(1, 0),
            tripType = TripType.ONE_WAY
        )

        every { fareCalcService.calcPrice(allAny(), allAny(), allAny(), allAny()) }.returns(
            FareCalcResult(
                BeforeDiscountedFare.from(Fare(Price(1000)), input.passengers),
                AfterDiscountedFare(Price(1000 * input.passengers.totalPassengers())),
                tripType = input.tripType
            )
        )
        every { surchargeCalcService.calcPrice(allAny(), allAny(), allAny(), allAny(), allAny(), allAny()) }.returns(
            SurchargeCalcResult(
                BeforeDiscountedSurcharge.from(Surcharge(Price(1000)), input.passengers),
                AfterDiscountedSurcharge(Price(1000 * input.passengers.totalPassengers())),
                input.tripType
            )
        )

        val priceCalculationUsecase = PriceCalculationUsecase(fareCalcService, surchargeCalcService)

        val result = priceCalculationUsecase.calc(
            input.departureStationName,
            input.arrivalStationName,
            input.tripType,
            input.trainType,
            input.seatType,
            input.passengers,
            input.departureDate
        )

        Assertions.assertEquals(1000, result.farePrice)
        Assertions.assertEquals(1000, result.surchargePrice)
    }

    @Test
    fun `割引されない場合の往復料金の金額が取得できること`() {
        val input = UsecaseInputCreator.create(
            passengers = Passengers(1, 0),
            tripType = TripType.ROUND_TRIP
        )

        every { fareCalcService.calcPrice(allAny(), allAny(), allAny(), allAny()) }.returns(
            FareCalcResult(
                BeforeDiscountedFare.from(Fare(Price(1000)), input.passengers),
                AfterDiscountedFare(Price(1000 * input.passengers.totalPassengers())),
                tripType = input.tripType
            )
        )
        every { surchargeCalcService.calcPrice(allAny(), allAny(), allAny(), allAny(), allAny(), allAny()) }.returns(
            SurchargeCalcResult(
                BeforeDiscountedSurcharge.from(Surcharge(Price(1000)), input.passengers),
                AfterDiscountedSurcharge(Price(1000 * input.passengers.totalPassengers())),
                input.tripType
            )
        )

        val priceCalculationUsecase = PriceCalculationUsecase(fareCalcService, surchargeCalcService)

        val result = priceCalculationUsecase.calc(
            input.departureStationName,
            input.arrivalStationName,
            input.tripType,
            input.trainType,
            input.seatType,
            input.passengers,
            input.departureDate
        )

        Assertions.assertEquals(2000, result.farePrice)
        Assertions.assertEquals(2000, result.surchargePrice)
    }
}