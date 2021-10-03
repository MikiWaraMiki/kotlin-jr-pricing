package domain.model.surcharge

import domain.model.shared.Price
import domain.model.season.SeasonType
import domain.model.surcharge.additionalcharge.NozomiAdditionalChargeTable
import domain.model.ticket.Ticket
import domain.model.train.SeatType
import domain.model.train.TrainType

/**
 * 特急料金計算クラス
 */
class SurchargeCalcService {
    private val surchargeTable = SurchargeTable()
    private val nozomiAdditionalChargeTable = NozomiAdditionalChargeTable()
    // 自由席割引料金
    private val NON_RESERVED_DISCOUNT_PRICE = Price(530)

    fun calcPrice(ticket: Ticket): Price {
        val surchargeBasePrice = surchargeTable.price(ticket.route)
        val surcharge = Surcharge(surchargeBasePrice)

        if (ticket.useNozomi()) {
            val additionalPrice = nozomiAdditionalChargeTable.price(ticket.route)
            surcharge.addPrice(additionalPrice)
        }

        if (ticket.useNonReservedSeat()) {
            surcharge.discountPrice(NON_RESERVED_DISCOUNT_PRICE)
        }

        if (ticket.useReservedSeat()) {
            seasonTypeFareCalc(ticket, surcharge)
        }

        return surcharge.price(ticket.isChild)
    }

    // TODO: surchargeを不変クラスにして切り出す
    private fun seasonTypeFareCalc(ticket: Ticket, surcharge: Surcharge) {
        when(SeasonType.of(ticket.departureDate.date)) {
            SeasonType.OFF_PEAK -> {
                surcharge.discountPrice(Price(200))
            }
            SeasonType.PEAK -> {
                surcharge.addPrice(Price(200))
            }
            else -> {}
        }
    }
}