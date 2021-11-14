package jrpricing.catalog.domain.model.route

import jrpricing.catalog.domain.model.station.StationId

interface RouteRepository {
    fun findByDepartureAndArrivalStation(departureStationId: StationId, arrivalStationId: StationId): Route?
}