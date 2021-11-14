package jrpricing.catalog.presentation.api.station

import jrpricing.catalog.domain.model.station.StationId
import jrpricing.catalog.usecase.station.FetchByStationIdUsecase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/station")
class StationController(
    private val fetchByStationIdUsecase: FetchByStationIdUsecase
) {
    @GetMapping("")
    fun findStationById(
        @RequestParam(name = "stationId") stationId: String
    ): FindStationByIdResponse {
        val station = fetchByStationIdUsecase.execute(getStationId(stationId))

        return FindStationByIdResponse(
            id = station.stationId.value,
            name = station.name()
        )
    }

    private fun getStationId(stationId: String): StationId {
        return StationId.reConstructor(stationIdString = stationId)
    }
}