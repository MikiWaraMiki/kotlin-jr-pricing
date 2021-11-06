package domain.model.discount.distance

import domain.model.discount.DiscountRule
import domain.model.route.Route
import domain.model.ticket.TripType

/**
 * 長距離移動割引適用ルール
 */
class DistanceKilometerDiscountRule(
    private val tripType: TripType,
    private val route: Route
): DiscountRule {

    override fun can(): Boolean {
        if (tripType.isOneway()) return  false

        return route.isLongDistance()
    }
}