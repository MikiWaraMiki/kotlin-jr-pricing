package jrpricing.catalog.infra.repository

import jrpricing.catalog.testdata.route.RouteTestDataCreator
import jrpricing.catalog.testdata.station.StationTestDataCreator
import jrpricing.catalog.testdata.surcharge.NozomiAdditionalChargeTestDataCreator
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
internal class NozomiAdditionalChargeJooqRepositoryTest(
    @Autowired private val nozomiAdditionalChargeJooqRepository: NozomiAdditionalChargeJooqRepository,
    @Autowired private val stationTestDataCreator: StationTestDataCreator,
    @Autowired private val routeTestDataCreator: RouteTestDataCreator,
    @Autowired private val nozomiAdditionalChargeDataCreator: NozomiAdditionalChargeTestDataCreator
) {

    @Nested
    @DisplayName("findByRouteIDのテスト")
    inner class FindByRouteIdTest() {
        @Test
        fun `指定した経路IDののぞみ加算料金が存在する場合は、NozomiAdditionalChargeを返す`() {
            val arrival = stationTestDataCreator.create(stationName = "tokyo")
            val departure = stationTestDataCreator.create(stationName = "shin_osaka")
            val route = routeTestDataCreator.create(arrivalStationId = arrival.stationId, departureStationId = departure.stationId)
            val nozomiAdditionalCharge = nozomiAdditionalChargeDataCreator.create(route = route)

            val result = nozomiAdditionalChargeJooqRepository.findByRouteId(route.routeId)!!

            Assertions.assertEquals(nozomiAdditionalCharge.routeId, result.routeId)
            Assertions.assertEquals(nozomiAdditionalCharge.amount, result.amount)
        }

        @Test
        fun `指定した経路IDの加算料金が存在しない場合は、nullを返す`() {
            val arrival = stationTestDataCreator.create(stationName = "tokyo")
            val departure = stationTestDataCreator.create(stationName = "shin_osaka")
            val route = routeTestDataCreator.create(arrivalStationId = arrival.stationId, departureStationId = departure.stationId)

            val result = nozomiAdditionalChargeJooqRepository.findByRouteId(route.routeId)

            Assertions.assertNull(result)
        }
    }

}