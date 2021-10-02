package domain.model.discount.rule

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
    private val DISCOUNT_RATE = 0.1

    /**
     * 片道の移動距離に応じて割引が適用可能か確かめる
     */
    override fun can(): Boolean {
        if (ticket.isOneway()) return  false

        return ticket.isLongDistance()
    }
}