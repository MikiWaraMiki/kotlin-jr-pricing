package jrpricing.discount.domain.roundtrip

import jrpricing.discount.domain.route.Route
import jrpricing.discount.domain.shared.TripType

/**
 * 往復割引適用条件
 */
class RoundTripAppliedPolicy {
    companion object {
        private const val LARGE_DISTANCE_KILOMETER = 601

        fun can(route: Route, tripType: TripType): Boolean {
            if (!tripType.isRoundTrip()) return false

            return route.kilometer() >= LARGE_DISTANCE_KILOMETER
        }
    }
}