package domain.model.discount

import domain.model.fare.Fare
import domain.model.shared.Price
import domain.model.ticket.Ticket

interface DiscountRule {
    /**
     * 割引が適用可能か検証する
     */
    fun can(): Boolean

    /**
     * 割引適用後の運賃を算出
     */
    fun price(fare: Fare): Fare
}