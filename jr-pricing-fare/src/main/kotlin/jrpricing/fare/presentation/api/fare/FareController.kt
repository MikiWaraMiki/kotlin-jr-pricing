package jrpricing.fare.presentation.api.fare

import jrpricing.fare.domain.shared.TripRoute
import jrpricing.fare.domain.shared.TripType
import jrpricing.fare.usecase.fare.FindFareByStationIDUsecase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/fare")
class FareController(
    private val findFareByStationIDUsecase: FindFareByStationIDUsecase
) {

    @GetMapping("/search")
    fun search(
        @RequestParam(name = "departureStationId") departureStationId: String,
        @RequestParam(name = "arrivalStationId") arrivalStationId: String,
        @RequestParam(name = "tripTypeName") tripTypeName: String,
    ): SearchResponse {
        val result = findFareByStationIDUsecase.execute(
            getTripRoute(departureStationId, arrivalStationId),
            getTripType(tripTypeName)
        )

        return SearchResponse(
            amount = result.amount.value
        )
    }

    private fun getTripRoute(departureStationId: String, arrivalStationId: String): TripRoute {
        return TripRoute(departureStationId, arrivalStationId)
    }

    private fun getTripType(tripTypeName: String): TripType {
        return TripType.of(tripTypeName)
    }
}