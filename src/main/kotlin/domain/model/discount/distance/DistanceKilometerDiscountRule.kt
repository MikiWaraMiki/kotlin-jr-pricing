package domain.model.discount.distance

import domain.model.discount.Discount
import domain.model.discount.rate.DiscountRate
import domain.model.discount.rule.DiscountRule
import domain.model.fare.Fare
import domain.model.route.Route
import domain.model.shared.Price
import domain.model.ticket.Ticket
import domain.model.ticket.TicketType

/**
 * 長距離移動割引適用ルール
 */
class DistanceKilometerDiscountRule(
    private val ticketType: TicketType,
    private val route: Route
): DiscountRule {

    override fun can(): Boolean {
        if (ticketType.isOneway()) return  false

        return route.isLongDistance()
    }

    override fun rate(): DiscountRate {
        return DiscountRate.RATE_10
    }
}