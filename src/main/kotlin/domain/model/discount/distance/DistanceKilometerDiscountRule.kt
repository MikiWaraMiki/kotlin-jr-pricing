package domain.model.discount.distance

import domain.model.discount.DiscountRule
import domain.model.route.Route
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
}