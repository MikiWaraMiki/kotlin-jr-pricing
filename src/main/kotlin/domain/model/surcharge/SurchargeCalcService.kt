package domain.model.surcharge

import domain.model.discount.SurchargeDiscountCalcService
import domain.model.route.Route
import domain.model.shared.Price
import domain.model.season.SeasonType
import domain.model.shared.Passengers
import domain.model.surcharge.additionalcharge.NozomiAdditionalChargeTable
import domain.model.ticket.DepartureDate
import domain.model.ticket.Ticket
import domain.model.train.SeatType
import domain.model.train.TrainType

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