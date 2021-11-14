package jrpricing.demo.first.usecase.price

import jrpricing.demo.first.domain.model.exception.DomainException
import jrpricing.demo.first.domain.model.fare.*
import jrpricing.demo.first.domain.model.shared.Passengers
import jrpricing.demo.first.domain.model.surcharge.*
import jrpricing.demo.first.domain.model.ticket.TripType
import jrpricing.demo.first.usecase.price.testdata.UsecaseInputCreator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PriceCalculationUsecaseTest {
    @Test
    fun `存在しない出発駅を指定した場合はエラーが発生する`() {
        val input = UsecaseInputCreator.create(
            departureStationName = "miyagi"
        )

        val priceCalculationUsecase = PriceCalculationUsecase()

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

        val priceCalculationUsecase = PriceCalculationUsecase()

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

        val priceCalculationUsecase = PriceCalculationUsecase()

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


        val priceCalculationUsecase = PriceCalculationUsecase()

        val result = priceCalculationUsecase.calc(
            input.departureStationName,
            input.arrivalStationName,
            input.tripType,
            input.trainType,
            input.seatType,
            input.passengers,
            input.departureDate
        )

        Assertions.assertEquals(8910, result.farePrice)
        Assertions.assertEquals(5290, result.surchargePrice)
    }

    @Test
    fun `割引されない場合の往復料金の金額が取得できること`() {
        val input = UsecaseInputCreator.create(
            passengers = Passengers(1, 0),
            tripType = TripType.ROUND_TRIP
        )


        val priceCalculationUsecase = PriceCalculationUsecase()

        val result = priceCalculationUsecase.calc(
            input.departureStationName,
            input.arrivalStationName,
            input.tripType,
            input.trainType,
            input.seatType,
            input.passengers,
            input.departureDate
        )

        Assertions.assertEquals(17820, result.farePrice)
        Assertions.assertEquals(10580, result.surchargePrice)
    }
}