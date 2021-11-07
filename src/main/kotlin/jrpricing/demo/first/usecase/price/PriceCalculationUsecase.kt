package jrpricing.demo.first.usecase.price

import jrpricing.demo.first.domain.model.fare.FareCalcService
import jrpricing.demo.first.domain.model.route.Route
import jrpricing.demo.first.domain.model.shared.Passengers
import jrpricing.demo.first.domain.model.station.Station
import jrpricing.demo.first.domain.model.surcharge.SurchargeCalcService
import jrpricing.demo.first.domain.model.ticket.DepartureDate
import jrpricing.demo.first.domain.model.ticket.TripType
import jrpricing.demo.first.domain.model.train.SeatType
import jrpricing.demo.first.domain.model.train.TrainType
import java.time.LocalDate

/**
 * 料金計算ユースケースクラス
 */
class PriceCalculationUsecase(
    private val fareCalcService: FareCalcService,
    private val surchargeCalcService: SurchargeCalcService
) {

    fun calc(
        departureStationName: String,
        arrivalStationName: String,
        tripType: TripType,
        trainType: TrainType,
        seatType: SeatType,
        passengers: Passengers,
        departureLocalDate: LocalDate
    ): PriceCalculationUsecaseDto {

        val departureStation = Station.fromLabel(departureStationName)
        val arrivalStation = Station.fromLabel(arrivalStationName)
        val route = Route(departureStation, arrivalStation)

        val departureDate = DepartureDate(departureLocalDate)

        val fareCalcResult = fareCalcService.calcPrice(route, passengers, departureDate, tripType)

        val surchargeCalcResult = surchargeCalcService.calcPrice(route, trainType, seatType, departureDate, passengers)

        return PriceCalculationUsecaseDto(fareCalcResult.amount().value, surchargeCalcResult.amount().value)
    }
}