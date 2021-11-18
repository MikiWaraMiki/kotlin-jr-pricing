package jrpricing.catalog.usecase.basicsurcharge

import io.mockk.every
import io.mockk.mockk
import jrpricing.catalog.domain.model.route.RouteRepository
import jrpricing.catalog.domain.model.shared.Amount
import jrpricing.catalog.domain.model.station.StationId
import jrpricing.catalog.domain.model.surcharge.BasicSurchargeRepository
import jrpricing.catalog.testdata.route.TestRouteFactory
import jrpricing.catalog.testdata.surcharge.TestBasicSurchargeFactory
import jrpricing.catalog.usecase.exception.AssertionFailException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class FindBasicSurchargeUsecaseTest {
    private val routeRepository: RouteRepository = mockk()
    private val basicSurchargeRepository: BasicSurchargeRepository = mockk()

    @Test
    fun `指定した駅間の経路が存在しない場合はAssertionFailExceptionが発生すること`() {
        val departureStationId = StationId.create()
        val arrivalStationId = StationId.create()

        every { routeRepository.findByDepartureAndArrivalStation(departureStationId, arrivalStationId) }.returns(null)

        val usecase = FindBasicSurchargeUsecase(routeRepository, basicSurchargeRepository)

        val target: () -> Unit = {
            usecase.execute(departureStationId, arrivalStationId)
        }

        val exception = assertThrows<AssertionFailException>(target)

        Assertions.assertEquals("指定した駅間の経路は存在しません", exception.message)
    }

    @Test
    fun `指定した経路の特急料金が登録されていない場合は、AssertionFailExceptionが発生すること`() {
        val departureStationId = StationId.create()
        val arrivalStationId = StationId.create()
        val route = TestRouteFactory.create(departureStationId = departureStationId, arrivalStationId = arrivalStationId)

        every { routeRepository.findByDepartureAndArrivalStation(departureStationId, arrivalStationId) }.returns(route)
        every{ basicSurchargeRepository.findByRouteId(route.routeId) }.returns(null)
        val usecase = FindBasicSurchargeUsecase(routeRepository, basicSurchargeRepository)

        val target: () -> Unit = {
            usecase.execute(departureStationId, arrivalStationId)
        }

        val exception = assertThrows<AssertionFailException>(target)

        Assertions.assertEquals("指定した経路の特急料金は存在しません", exception.message)
    }

    @Test
    fun `指定した駅間の特急料金が存在する場合は、BasicSurchargeを返すこと`() {
        val departureStationId = StationId.create()
        val arrivalStationId = StationId.create()
        val route = TestRouteFactory.create(departureStationId = departureStationId, arrivalStationId = arrivalStationId)
        val basicSurcharge = TestBasicSurchargeFactory.create(routeId = route.routeId, amount = Amount(1000))

        every { routeRepository.findByDepartureAndArrivalStation(departureStationId, arrivalStationId) }.returns(route)
        every{ basicSurchargeRepository.findByRouteId(route.routeId) }.returns(basicSurcharge)
        val usecase = FindBasicSurchargeUsecase(routeRepository, basicSurchargeRepository)

        val result = usecase.execute(departureStationId, arrivalStationId)

        Assertions.assertEquals(basicSurcharge.routeId, result.routeId)
        Assertions.assertEquals(basicSurcharge.amount, result.amount)

    }
}