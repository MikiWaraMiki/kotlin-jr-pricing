package jrpricing.catalog.presentation.api.route

import jrpricing.catalog.domain.model.station.StationId
import jrpricing.catalog.presentation.api.dto.RouteDto
import jrpricing.catalog.presentation.api.dto.StationDto
import jrpricing.catalog.usecase.route.FindRouteFromDepartureAndArrivalUsecase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/route")
class RouteController(
    private val findRouteFromDepartureAndArrivalUsecase: FindRouteFromDepartureAndArrivalUsecase
) {
    @GetMapping("/search")
    fun search(
        @RequestParam(name = "departureStationId") departureStationId: String,
        @RequestParam(name = "arrivalStationId") arrivalStationId: String
    ): RouteDto {
        val result = findRouteFromDepartureAndArrivalUsecase.execute(
            getStationId(departureStationId),
            getStationId(arrivalStationId)
        )

        return RouteDto(
            id = result.routeId.value,
            distance = result.distance.halfTripDistance(),
            departureStation = StationDto(
                id = result.departureStation.stationId.value,
                name = result.departureStation.name()
            ),
            arrivalStation = StationDto(
                id = result.arrivalStation.stationId.value,
                name = result.arrivalStation.name()
            )
        )
    }

    private fun getStationId(stationId: String): StationId {
        return StationId.reConstructor(stationId)
    }
}