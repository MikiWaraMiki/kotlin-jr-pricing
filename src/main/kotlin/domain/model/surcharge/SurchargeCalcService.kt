package domain.model.surcharge

import domain.model.shared.Price
import domain.model.shared.Route
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

        if (ticket.trainType == TrainType.NOZOMI) {
            val additionalPrice = nozomiAdditionalChargeTable.price(ticket.route)
            surcharge.addPrice(additionalPrice)
        }

        if (ticket.seatType == SeatType.NON_RESERVED) {
            surcharge.discountPrice(NON_RESERVED_DISCOUNT_PRICE)
        }

        return surcharge.price(ticket.isChild)
    }
}