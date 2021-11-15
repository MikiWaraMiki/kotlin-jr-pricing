package jrpricing.catalog.infra.repository

import jrpricing.catalog.testdata.fare.BasicFareTestDataCreator
import jrpricing.catalog.testdata.fare.TestBasicFareFactory
import jrpricing.catalog.testdata.route.RouteTestDataCreator
import jrpricing.catalog.testdata.route.TestRouteFactory
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
internal class BasicFareJooqRepositoryTest(
    @Autowired private val basicFareJooqRepository: BasicFareJooqRepository,
    @Autowired private val stationTestDataCreator: StationTestDataCreator,
    @Autowired private val routeTestDataCreator: RouteTestDataCreator,
    @Autowired private val basicFareTestDataCreator: BasicFareTestDataCreator
) {
    @Nested
    @DisplayName("findByRouteIdのテスト")
    inner class FindByRouteIdTest() {
        @Test
        fun `指定した経路IDの運賃が存在する場合は、BasicFareを返す`() {
            val arrival = stationTestDataCreator.create(stationName = "tokyo")
            val departure = stationTestDataCreator.create(stationName = "shin_osaka")
            val route = routeTestDataCreator.create(arrivalStationId = arrival.stationId, departureStationId = departure.stationId)
            val basicFare = basicFareTestDataCreator.create(route = route)

            val result = basicFareJooqRepository.findByRouteId(route.routeId)!!

            Assertions.assertEquals(basicFare.routeId, result.routeId)
            Assertions.assertEquals(basicFare.amount, result.amount)
        }

        @Test
        fun `指定した経路IDの運賃が存在しない場合は、nullを返すこと`() {
            val route = TestRouteFactory.create()

            val result = basicFareJooqRepository.findByRouteId(route.routeId)

            Assertions.assertNull(result)
        }
    }
}