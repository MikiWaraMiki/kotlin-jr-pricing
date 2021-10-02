package domain.model.discount.rule

import domain.model.fare.Fare
import domain.model.shared.Price
import domain.model.ticket.Ticket

interface DiscountRule {
    /**
     * 割引が適用可能か検証する
     */
    fun can(): Boolean
}