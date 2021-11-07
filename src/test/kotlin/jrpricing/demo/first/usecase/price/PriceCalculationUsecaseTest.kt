package jrpricing.demo.first.usecase.price

import io.mockk.every
import io.mockk.mockk
import jrpricing.demo.first.domain.model.exception.DomainException
import jrpricing.demo.first.domain.model.fare.*
import jrpricing.demo.first.domain.model.surcharge.SurchargeCalcService
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
}