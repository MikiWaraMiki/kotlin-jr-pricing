package jrpricing.demo.first.domain.model.surcharge

import jrpricing.demo.first.domain.model.discount.SurchargeDiscountCalcService
import jrpricing.demo.first.domain.model.route.Route
import jrpricing.demo.first.domain.model.shared.Passengers
import jrpricing.demo.first.domain.model.ticket.DepartureDate
import jrpricing.demo.first.domain.model.train.SeatType
import jrpricing.demo.first.domain.model.train.TrainType

/**
 * 特急料金計算クラス
 */
class SurchargeCalcService(
    private val surchargeDiscountCalcService: SurchargeDiscountCalcService
) {
    private val surchargeTable = SurchargeTable()

    fun calcPrice(
        route: Route,
        trainType: TrainType,
        seatType: SeatType,
        departureDate: DepartureDate,
        passengers: Passengers
    ): SurchargeCalcResult {
        val surchargeBasePrice = surchargeTable.price(route)
        val baseSurcharge = Surcharge(surchargeBasePrice)

        val trainTypeSurchargeAddedResult = TrainTypeSurcharge.calc(surcharge = baseSurcharge, route, trainType)

        val seatTypeAddedResult = SeatTypeSurcharge.calc(trainTypeSurchargeAddedResult, seatType, departureDate)

        val beforeDiscountedSurcharge = BeforeDiscountedSurcharge.from(seatTypeAddedResult, passengers)

        val discountedResultPrice = surchargeDiscountCalcService.calc(seatTypeAddedResult, passengers)

        val afterDiscountedSurcharge = AfterDiscountedSurcharge(discountedResultPrice)

        return SurchargeCalcResult(beforeDiscountedSurcharge, afterDiscountedSurcharge)
    }
}