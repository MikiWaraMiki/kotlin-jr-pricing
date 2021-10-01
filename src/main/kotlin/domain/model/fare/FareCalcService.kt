package domain.model.fare

import domain.model.shared.Price
import domain.model.ticket.Ticket

/**
 * 運賃計算サービス
 */
class FareCalcService {
    private val fareTable = FareTable()

    fun calcPrice(ticket: Ticket): Price {
        val farePrice = fareTable.price(ticket.route)
        val fare = Fare(farePrice)

        return fare.price(ticket.isChild)
    }
}