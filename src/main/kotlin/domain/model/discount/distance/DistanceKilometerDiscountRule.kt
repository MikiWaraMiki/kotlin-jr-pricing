package domain.model.discount.distance

import domain.model.discount.DiscountRule
import domain.model.route.Route
import domain.model.ticket.TripType

/**
 * 長距離移動割引適用ルール
 */
class DistanceKilometerDiscountRule(
    private val tripType: TripType,
    private val tripDistance: Int
): DiscountRule {

    override fun can(): Boolean {
        if (tripType.isOneway()) return  false

       return tripDistance >= 601
    }

    companion object {
        private const val LONG_DISTANCE = 601
    }
}