package jrpricing.catalog.infra.repository

import jrpricing.catalog.domain.model.station.Station
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
internal class StationJooqRepositoryTest(
    @Autowired private val stationRepository: StationJooqRepository,
    @Autowired private val stationTestDataCreator: StationTestDataCreator
) {
    @Nested
    @DisplayName("insertテスト")
    inner class FindByAndInsertTest() {
        @Test
        fun `insertしたものがfindByIdで取得できること`() {
            val station: Station = stationTestDataCreator.create()

            val foundStation = stationRepository.findById(station.stationId)!!

            Assertions.assertEquals(station.stationId, foundStation.stationId)
            Assertions.assertEquals(station.name(), foundStation.name())
        }
    }
}