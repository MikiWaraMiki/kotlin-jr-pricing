package jrpricing.catalog.presentation.api.basicfare

import jrpricing.catalog.domain.model.station.StationId
import jrpricing.catalog.usecase.basicfare.FindBasicFareUsecase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/catalog/fare")
class BasicFareController(
    private val findBasicFareByRouteIdUsecase: FindBasicFareUsecase
) {

    @GetMapping("/search")
    fun search(
        @RequestParam(name = "departureStationId") departureStationId: String,
        @RequestParam(name = "arrivalStationId") arrivalStationId: String
    ): SearchFareResponse {
        val result = findBasicFareByRouteIdUsecase.execute(
            getStationId(departureStationId),
            getStationId(arrivalStationId)
        )

        return SearchFareResponse(
            result.routeId.value,
            result.amount.value
        )
    }

    private fun getStationId(stationId: String): StationId {
        return StationId.reConstructor(stationId)
    }
}