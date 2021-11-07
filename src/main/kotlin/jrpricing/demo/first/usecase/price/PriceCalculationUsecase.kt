package jrpricing.demo.first.usecase.price

import jrpricing.demo.first.domain.model.discount.FareDiscountCalcService
import jrpricing.demo.first.domain.model.discount.SurchargeDiscountCalcService
import jrpricing.demo.first.domain.model.fare.FareCalcService
import jrpricing.demo.first.domain.model.route.JrRouteTable
import jrpricing.demo.first.domain.model.route.Route
import jrpricing.demo.first.domain.model.shared.Passengers
import jrpricing.demo.first.domain.model.station.Station
import jrpricing.demo.first.domain.model.surcharge.SurchargeCalcService
import jrpricing.demo.first.domain.model.ticket.DepartureDate
import jrpricing.demo.first.domain.model.ticket.TripType
import jrpricing.demo.first.domain.model.train.SeatType
import jrpricing.demo.first.domain.model.train.TrainType
import org.springframework.stereotype.Service
import java.time.LocalDate

/**
 * 料金計算ユースケースクラス
 */
@Service
class PriceCalculationUsecase() {

    fun calc(
        departureStationName: String,
        arrivalStationName: String,
        tripType: TripType,
        trainType: TrainType,
        seatType: SeatType,
        passengers: Passengers,
        departureDate: DepartureDate
    ): PriceCalculationUsecaseDto {

        val departureStation = Station.fromLabel(departureStationName)
        val arrivalStation = Station.fromLabel(arrivalStationName)
        val route = JrRouteTable.of(departureStation, arrivalStation)

        val fareDiscountCalcService = FareDiscountCalcService.withGenerateRule(tripType, route.distance(), passengers)
        val fareCalcService = FareCalcService(fareDiscountCalcService)
        val fareCalcResult = fareCalcService.calcPrice(route, passengers, departureDate, tripType)

        val surchargeDiscountCalcService = SurchargeDiscountCalcService.withGenerateRule(passengers)
        val surchargeCalcService = SurchargeCalcService(surchargeDiscountCalcService)
        val surchargeCalcResult = surchargeCalcService.calcPrice(route, trainType, seatType, departureDate, passengers, tripType)

        return PriceCalculationUsecaseDto(fareCalcResult.amount().value, surchargeCalcResult.amount().value)
    }
}