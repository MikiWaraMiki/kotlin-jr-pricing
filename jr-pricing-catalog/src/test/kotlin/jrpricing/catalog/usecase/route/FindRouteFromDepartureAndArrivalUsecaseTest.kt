package jrpricing.catalog.usecase.route

import io.mockk.every
import io.mockk.mockk
import jrpricing.catalog.domain.model.route.RouteRepository
import jrpricing.catalog.domain.model.station.StationName
import jrpricing.catalog.domain.repository.station.StationRepository
import jrpricing.catalog.testdata.route.TestRouteFactory
import jrpricing.catalog.testdata.station.TestStationFactory
import jrpricing.catalog.usecase.exception.AssertionFailException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class FindRouteFromDepartureAndArrivalUsecaseTest {
    private val routeRepository: RouteRepository = mockk()
    private val stationRepository: StationRepository = mockk()
    private val usecase = FindRouteFromDepartureAndArrivalUsecase(
        routeRepository,
        stationRepository
    )

    @Test
    fun `２つのリポジトリから値を取得できる場合は、結果がDTOに詰め替えて返されること`() {
        val departureStation = TestStationFactory.create(name = StationName("tokyo"))
        val arrivalStation = TestStationFactory.create(name = StationName("shin_osaka"))
        val route = TestRouteFactory.create(
            departureStationId = departureStation.stationId,
            arrivalStationId = arrivalStation.stationId
        )

        every {
            routeRepository.findByDepartureAndArrivalStation(
                departureStation.stationId,
                arrivalStation.stationId
            )
        }.returns(route)
        every { stationRepository.findById(departureStation.stationId) }.returns(departureStation)
        every { stationRepository.findById(arrivalStation.stationId) }.returns(arrivalStation)

        val resultDTO = usecase.execute(departureStation.stationId, arrivalStation.stationId)

        val expectedDto = FindRouteFromDepartureAndArrivalUsecaseResultDto(
            route.routeId,
            departureStation,
            arrivalStation
        )

        Assertions.assertEquals(expectedDto, resultDTO)

    }

    @Test
    fun `登録されていない経路の場合は、AssertionFailExceptionが発生すること`() {
        val departureStation = TestStationFactory.create(name = StationName("tokyo"))
        val arrivalStation = TestStationFactory.create(name = StationName("shin_osaka"))

        every {
            routeRepository.findByDepartureAndArrivalStation(
                departureStation.stationId,
                arrivalStation.stationId
            )
        }.returns(null)

        val target: () -> Unit = {
            usecase.execute(departureStation.stationId, arrivalStation.stationId)
        }

        val exception = assertThrows<AssertionFailException>(target)

        Assertions.assertEquals("存在しない経路です", exception.message)
    }

    @Test
    fun `経路上には登録されているが、駅のデータが存在しない場合は、RuntimeExceptionが発生すること`() {
        val departureStation = TestStationFactory.create(name = StationName("tokyo"))
        val arrivalStation = TestStationFactory.create(name = StationName("shin_osaka"))
        val route = TestRouteFactory.create(
            departureStationId = departureStation.stationId,
            arrivalStationId = arrivalStation.stationId
        )

        every {
            routeRepository.findByDepartureAndArrivalStation(
                departureStation.stationId,
                arrivalStation.stationId
            )
        }.returns(route)
        every { stationRepository.findById(departureStation.stationId) }.returns(departureStation)
        every { stationRepository.findById(arrivalStation.stationId) }.returns(null)

        val target: () -> Unit = {
            usecase.execute(departureStation.stationId, arrivalStation.stationId)
        }

        val exception = assertThrows<RuntimeException>(target)

        Assertions.assertEquals("経路に登録されている駅が存在しませんでした", exception.message)
    }
}