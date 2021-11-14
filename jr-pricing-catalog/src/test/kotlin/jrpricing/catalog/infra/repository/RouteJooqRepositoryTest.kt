package jrpricing.catalog.infra.repository

import jrpricing.catalog.domain.model.route.RouteRepository
import jrpricing.catalog.domain.repository.station.StationRepository
import jrpricing.catalog.testdata.route.RouteTestDataCreator
import jrpricing.catalog.testdata.station.StationTestDataCreator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

@ExtendWith(SpringExtension::class)
@SpringBootTest
@Transactional
internal class RouteJooqRepositoryTest(
    @Autowired private val stationRepository: StationJooqRepository,
    @Autowired private val routeRepository: RouteJooqRepository,
    @Autowired private val stationTestDataCreator: StationTestDataCreator,
    @Autowired private val routeTestDataCreator: RouteTestDataCreator
) {

    @Nested
    @DisplayName("findByDepartureAndArrivalStationのテスト")
    inner class `FindByDepartureAndArrivalStationのテスト`() {
        @Test
        fun `経路に登録されている出発駅と到着駅の組み合わせの場合は、Routeオブジェクトを返却すること`() {
            val departureStation = stationTestDataCreator.create(stationName = "tokyo")
            val arrivalStation = stationTestDataCreator.create(stationName = "shin_osaka")

            val route = routeTestDataCreator.create(departureStation.stationId,arrivalStation.stationId)

            val foundRoute = routeRepository.findByDepartureAndArrivalStation(
                departureStation.stationId,
                arrivalStation.stationId
            )!!

            Assertions.assertEquals(route.routeId, foundRoute.routeId)
            Assertions.assertEquals(departureStation.stationId, foundRoute.departureStationId)
            Assertions.assertEquals(arrivalStation.stationId, foundRoute.arrivalStationId)
        }

        @Test
        fun `経路に登録されていない出発駅と到着駅の組み合わせの場合は、nullを返すこと`() {
            val departureStation = stationTestDataCreator.create(stationName = "tokyo")
            val arrivalStation = stationTestDataCreator.create(stationName = "shin_osaka")

            val maybeNull = routeRepository.findByDepartureAndArrivalStation(
                departureStation.stationId,
                arrivalStation.stationId
            )

            Assertions.assertNull(maybeNull)
        }
    }
}