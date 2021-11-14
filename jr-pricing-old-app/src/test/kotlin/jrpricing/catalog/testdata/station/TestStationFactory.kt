package jrpricing.catalog.testdata.station

import jrpricing.catalog.domain.model.station.Station
import jrpricing.catalog.domain.model.station.StationId
import jrpricing.catalog.domain.model.station.StationName

object TestStationFactory {
    fun create(
        stationId: StationId = StationId.create(),
        name: StationName = StationName("tokyo")
    ): Station {
        return Station.reConstructor(stationId, name)
    }
}