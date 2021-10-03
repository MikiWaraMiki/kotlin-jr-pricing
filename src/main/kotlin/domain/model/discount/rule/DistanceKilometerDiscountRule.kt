package domain.model.discount.rule

import domain.model.discount.rate.DiscountRate
import domain.model.discount.rule.DiscountRule
import domain.model.fare.Fare
import domain.model.shared.Price
import domain.model.ticket.Ticket

/**
 * 長距離移動割引適用ルール
 */
class DistanceKilometerDiscountRule(
    private val ticket: Ticket,
): DiscountRule {

    override fun can(): Boolean {
        if (ticket.isOneway()) return  false

        return ticket.isLongDistance()
    }

    override fun rate(): DiscountRate {
        return DiscountRate.RATE_10
    }
}