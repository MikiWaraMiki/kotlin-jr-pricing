package jrpricing.demo.first.presentation.controller.api.price

import jrpricing.demo.first.domain.model.shared.Passengers
import jrpricing.demo.first.domain.model.ticket.DepartureDate
import jrpricing.demo.first.domain.model.ticket.TripType
import jrpricing.demo.first.domain.model.train.SeatType
import jrpricing.demo.first.domain.model.train.TrainType
import jrpricing.demo.first.usecase.price.PriceCalculationUsecase
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/price")
class PriceCalcController(
    private val priceCalculationUsecase: PriceCalculationUsecase
) {

    @GetMapping("/calc")
    fun calculate(
        @RequestParam(name = "departureStation") departureStation: String,
        @RequestParam(name = "arrivalStation") arrivalStation: String,
        @RequestParam(name = "trainType") trainTypeName: String,
        @RequestParam(name = "seatType") seatTypeName: String,
        @RequestParam(name = "tripType") tripTypeName: String,
        @RequestParam(name = "adults") adults: Int,
        @RequestParam(name = "childs") childs: Int,
        @RequestParam(name = "departureDate") departureDate: String
    ): PriceCalculateResponse {
        val result = priceCalculationUsecase.calc(
            departureStation,
            arrivalStation,
            getTripType(tripTypeName),
            getTrainType(trainTypeName),
            getSeatType(seatTypeName),
            getPassenger(adults, childs),
            getDepartureDate(departureDate)
        )

        return PriceCalculateResponse(
            totalPrice = result.total
        )
    }

    private fun getTrainType(trainTypeName: String): TrainType {
        return TrainType.fromLabel(trainTypeName)
    }

    private fun getSeatType(seatTypeName: String): SeatType {
        return SeatType.fromLabel(seatTypeName)
    }

    private fun getTripType(tripTypeName: String): TripType {
        return TripType.of(tripTypeName)
    }

    private fun getPassenger(adults: Int, childs: Int): Passengers {
        return Passengers(adults, childs)
    }

    private fun getDepartureDate(departureDate: String): DepartureDate {
        return DepartureDate.of(departureDate)
    }
}