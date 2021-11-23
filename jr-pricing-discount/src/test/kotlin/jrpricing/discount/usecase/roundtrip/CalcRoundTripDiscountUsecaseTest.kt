package jrpricing.discount.usecase.roundtrip

import com.github.guepardoapps.kulid.ULID
import io.mockk.every
import io.mockk.mockk
import jrpricing.discount.domain.roundtrip.BeforeRoundTripDiscountedFare
import jrpricing.discount.domain.route.BusinessKilometer
import jrpricing.discount.domain.route.Route
import jrpricing.discount.domain.route.RouteRepository
import jrpricing.discount.domain.shared.Amount
import jrpricing.discount.domain.shared.TripType
import jrpricing.discount.usecase.exception.AssertionFailException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class CalcRoundTripDiscountUsecaseTest {
    private val mockRouteRepository: RouteRepository = mockk()

    @Test
    fun `団体割引が適用できる場合は、適用後の金額を返すこと`() {
        val departureStationId: String = ULID.random()
        val arrivalStationId: String = ULID.random()
        val tripType = TripType.ROUND_TRIP
        val beforeRoundTripDiscountedFare = BeforeRoundTripDiscountedFare(Amount.of(1000))

        every { mockRouteRepository.findByStationId(departureStationId, arrivalStationId) }.returns(
            Route(BusinessKilometer(601))
        )

        val usecase = CalcRoundTripDiscountUsecase(routeRepository = mockRouteRepository)

        val result = usecase.execute(departureStationId, arrivalStationId, tripType, beforeRoundTripDiscountedFare)

        val expected = Amount.of(900) // 10%割引

        Assertions.assertEquals(expected, result)
    }

    @Test
    fun `団体割引が適用できない場合は、適用前の金額を返すこと`() {
        val departureStationId: String = ULID.random()
        val arrivalStationId: String = ULID.random()
        val tripType = TripType.ROUND_TRIP
        val beforeRoundTripDiscountedFare = BeforeRoundTripDiscountedFare(Amount.of(1000))

        every { mockRouteRepository.findByStationId(departureStationId, arrivalStationId) }.returns(
            Route(BusinessKilometer(600))
        )

        val usecase = CalcRoundTripDiscountUsecase(routeRepository = mockRouteRepository)

        val result = usecase.execute(departureStationId, arrivalStationId, tripType, beforeRoundTripDiscountedFare)

        val expected = Amount.of(1000) // 10%割引

        Assertions.assertEquals(expected, result)
    }

    @Test
    fun `出発駅と到着駅の経路が存在しない場合はAssertionFailExceptionが発生すること`() {
        val departureStationId: String = ULID.random()
        val arrivalStationId: String = ULID.random()
        val tripType = TripType.ROUND_TRIP
        val beforeRoundTripDiscountedFare = BeforeRoundTripDiscountedFare(Amount.of(1000))

        every { mockRouteRepository.findByStationId(departureStationId, arrivalStationId) }.returns(null)

        val usecase = CalcRoundTripDiscountUsecase(routeRepository = mockRouteRepository)

        val target: () -> Unit = {
            usecase.execute(departureStationId, arrivalStationId, tripType, beforeRoundTripDiscountedFare)
        }

        val exception = assertThrows<AssertionFailException>(target)

        Assertions.assertEquals("存在しない経路です", exception.message)
    }
}