package domain.model.surcharge

import domain.model.route.Route
import domain.model.shared.Price
import domain.model.season.SeasonType
import domain.model.surcharge.additionalcharge.NozomiAdditionalChargeTable
import domain.model.ticket.DepartureDate
import domain.model.ticket.Ticket
import domain.model.train.SeatType
import domain.model.train.TrainType

/**
 * 特急料金計算クラス
 */
class SurchargeCalcService {
    private val surchargeTable = SurchargeTable()
    private val nozomiAdditionalChargeTable = NozomiAdditionalChargeTable()

    fun calcPrice(route: Route, trainType: TrainType, seatType: SeatType, departureDate: DepartureDate, isChild: Boolean): Price {
        val surchargeBasePrice = surchargeTable.price(route)
        val surcharge = Surcharge(surchargeBasePrice)

        if (trainType.isNozomi()) {
            val additionalPrice = nozomiAdditionalChargeTable.price(route)
            surcharge.addPrice(additionalPrice)
        }

        if (seatType.isReserved()) {
            seasonTypeFareCalc(departureDate, surcharge)
        }else {
            surcharge.discountPrice(NON_RESERVED_DISCOUNT_PRICE)
        }

        return surcharge.price(isChild)
    }

    // TODO: surchargeを不変クラスにして切り出す
    private fun seasonTypeFareCalc(departureDate: DepartureDate, surcharge: Surcharge) {
        when(SeasonType.of(departureDate.date)) {
            SeasonType.OFF_PEAK -> {
                surcharge.discountPrice(Price(200))
            }
            SeasonType.PEAK -> {
                surcharge.addPrice(Price(200))
            }
            else -> {}
        }
    }

    companion object {
        private val NON_RESERVED_DISCOUNT_PRICE = Price(530)
    }
}