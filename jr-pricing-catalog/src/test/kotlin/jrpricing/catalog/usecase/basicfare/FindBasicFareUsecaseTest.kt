package jrpricing.catalog.usecase.basicfare

import io.mockk.every
import io.mockk.mockk
import jrpricing.catalog.domain.model.fare.BasicFareRepository
import jrpricing.catalog.domain.model.route.RouteRepository
import jrpricing.catalog.domain.model.station.StationId
import jrpricing.catalog.testdata.fare.TestBasicFareFactory
import jrpricing.catalog.testdata.route.TestRouteFactory
import jrpricing.catalog.usecase.exception.AssertionFailException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class FindBasicFareUsecaseTest {
    private val routeRepository: RouteRepository = mockk()
    private val basicFareRepository: BasicFareRepository = mockk()

    @Test
    fun `指定した駅間の経路が存在しない場合は、AssertionFailExceptionが発生すること`() {
        val departureStationId = StationId.create()
        val arrivalStationId = StationId.create()

        every { routeRepository.findByDepartureAndArrivalStation(departureStationId, arrivalStationId) }.returns(null)

        val usecase = FindBasicFareUsecase(routeRepository, basicFareRepository)

        val target: () -> Unit = {
            usecase.execute(departureStationId, arrivalStationId)
        }

        val exception = assertThrows<AssertionFailException>(target)

        Assertions.assertEquals("指定した駅間の経路は存在しません", exception.message)
    }

    @Test
    fun `指定した駅間の運賃が存在しない場合は、AssertionFailExceptionが発生すること`() {
        val departureStationId = StationId.create()
        val arrivalStationId = StationId.create()
        val route = TestRouteFactory.create(departureStationId = departureStationId, arrivalStationId = arrivalStationId)

        every { routeRepository.findByDepartureAndArrivalStation(departureStationId, arrivalStationId) }.returns(route)
        every { basicFareRepository.findByRouteId(route.routeId) }.returns(null)

        val usecase = FindBasicFareUsecase(routeRepository, basicFareRepository)

        val target: () -> Unit = {
            usecase.execute(departureStationId, arrivalStationId)
        }

        val exception = assertThrows<AssertionFailException>(target)

        Assertions.assertEquals("指定した経路の運賃は存在しません", exception.message)
    }

    fun `指定した駅間の運賃が存在する場合は、運賃を返すこと`() {
        val departureStationId = StationId.create()
        val arrivalStationId = StationId.create()
        val route = TestRouteFactory.create(departureStationId = departureStationId, arrivalStationId = arrivalStationId)
        val basicFare = TestBasicFareFactory.create(routeId = route.routeId)

        every { routeRepository.findByDepartureAndArrivalStation(departureStationId, arrivalStationId) }.returns(route)
        every { basicFareRepository.findByRouteId(route.routeId) }.returns(basicFare)

        val usecase = FindBasicFareUsecase(routeRepository, basicFareRepository)

        val result = usecase.execute(departureStationId, arrivalStationId)

        Assertions.assertEquals(route.routeId, result.routeId)
        Assertions.assertEquals(basicFare.amount, result.amount)
    }
}