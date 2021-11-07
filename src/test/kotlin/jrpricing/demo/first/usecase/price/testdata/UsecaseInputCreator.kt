package jrpricing.demo.first.usecase.price.testdata

import jrpricing.demo.first.domain.model.shared.Passengers
import jrpricing.demo.first.domain.model.surcharge.testdata.DepartureDateCreator
import jrpricing.demo.first.domain.model.ticket.DepartureDate
import jrpricing.demo.first.domain.model.ticket.TripType
import jrpricing.demo.first.domain.model.train.SeatType
import jrpricing.demo.first.domain.model.train.TrainType

data class UsecaseInput(
    val departureStationName: String,
    val arrivalStationName: String,
    val tripType: TripType,
    val trainType: TrainType,
    val seatType: SeatType,
    val passengers: Passengers,
    val departureDate: DepartureDate
)
object UsecaseInputCreator {
    fun create(
        departureStationName: String = "tokyo",
        arrivalStationName: String = "shin_osaka",
        tripType: TripType = TripType.ROUND_TRIP,
        trainType: TrainType = TrainType.HIKARI,
        seatType: SeatType = SeatType.RESERVED,
        passengers: Passengers = Passengers(1, 0),
        departureDate: DepartureDate = DepartureDateCreator.OFF_PEAK_DEPARTURE_DATE
    ): UsecaseInput {
        return UsecaseInput(
            departureStationName,
            arrivalStationName,
            tripType,
            trainType,
            seatType,
            passengers,
            departureDate
        )
    }
}