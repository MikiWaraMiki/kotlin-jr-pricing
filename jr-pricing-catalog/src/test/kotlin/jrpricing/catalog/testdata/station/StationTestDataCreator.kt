package jrpricing.catalog.testdata.station

import jrpricing.catalog.domain.model.station.Station
import jrpricing.catalog.domain.model.station.StationName
import jrpricing.catalog.domain.model.station.StationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class StationTestDataCreator(
    @Autowired private val stationRepository: StationRepository
) {
    fun create(stationName: String = "tokyo"): Station {
        val station = TestStationFactory.create(name = StationName(stationName))
        stationRepository.insert(station)
        return station
    }
}