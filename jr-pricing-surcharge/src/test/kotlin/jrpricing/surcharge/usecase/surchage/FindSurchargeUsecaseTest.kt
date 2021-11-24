package jrpricing.surcharge.usecase.surchage

import com.github.guepardoapps.kulid.ULID
import io.mockk.every
import io.mockk.mockk
import jrpricing.surcharge.domain.shared.Amount
import jrpricing.surcharge.domain.shared.SeatType
import jrpricing.surcharge.domain.shared.TrainType
import jrpricing.surcharge.domain.shared.TripRoute
import jrpricing.surcharge.domain.surcharge.ReserveSeatSurcharge
import jrpricing.surcharge.domain.surcharge.ReserveSeatSurchargeRepository
import jrpricing.surcharge.domain.testdata.TestDepartureMonthDayFactory
import jrpricing.surcharge.usecase.exception.AssertionFailException
import jrpricing.surcharge.usecase.surcharge.FindSurchargeUsecase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class FindSurchargeUsecaseTest {
    private val reserveSeatSurchargeRepository: ReserveSeatSurchargeRepository = mockk()

    @Test
    fun `存在する経路の場合、指定席の特急料金が取得できること`() {
        val departureStationId = ULID.random()
        val arrivalStationId = ULID.random()
        val tripRoute = TripRoute(departureStationId, arrivalStationId)
        val seatType = SeatType.RESERVED
        val trainType = TrainType.HIKARI
        val departureMonthDay = TestDepartureMonthDayFactory.createRegularDepartureMonthDay()

        every { reserveSeatSurchargeRepository.findByTripRoute(tripRoute, trainType) }.returns(
            ReserveSeatSurcharge(Amount.of(10000))
        )
        val usecase = FindSurchargeUsecase(reserveSeatSurchargeRepository)

        val surcharge = usecase.execute(tripRoute, seatType, trainType, departureMonthDay)

        val expected = Amount.of(10000)

        Assertions.assertEquals(expected, surcharge.amount)
    }

    @Test
    fun `存在する経路の場合、自由席の特急料金が取得できること`() {
        val departureStationId = ULID.random()
        val arrivalStationId = ULID.random()
        val tripRoute = TripRoute(departureStationId, arrivalStationId)
        val seatType = SeatType.NON_RESERVED
        val trainType = TrainType.HIKARI
        val departureMonthDay = TestDepartureMonthDayFactory.createRegularDepartureMonthDay()

        every { reserveSeatSurchargeRepository.findByTripRoute(tripRoute, trainType) }.returns(
            ReserveSeatSurcharge(Amount.of(10000))
        )
        val usecase = FindSurchargeUsecase(reserveSeatSurchargeRepository)

        val surcharge = usecase.execute(tripRoute, seatType, trainType, departureMonthDay)

        val expected = Amount.of(9470)

        Assertions.assertEquals(expected, surcharge.amount)

    }

    @Test
    fun `存在しない経路の場合、AssertionFailExceptionが発生すること`() {
        val departureStationId = ULID.random()
        val arrivalStationId = ULID.random()
        val tripRoute = TripRoute(departureStationId, arrivalStationId)
        val seatType = SeatType.NON_RESERVED
        val trainType = TrainType.HIKARI
        val departureMonthDay = TestDepartureMonthDayFactory.createRegularDepartureMonthDay()

        every { reserveSeatSurchargeRepository.findByTripRoute(tripRoute, trainType) }.returns(
            null
        )
        val usecase = FindSurchargeUsecase(reserveSeatSurchargeRepository)

        val target: () -> Unit = {
            usecase.execute(tripRoute, seatType, trainType, departureMonthDay)
        }

        val exception = assertThrows<AssertionFailException>(target)

        Assertions.assertEquals("存在しない経路です", exception.message)
    }
}