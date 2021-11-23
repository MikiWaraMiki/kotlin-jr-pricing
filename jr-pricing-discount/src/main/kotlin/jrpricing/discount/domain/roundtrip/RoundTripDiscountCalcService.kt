package jrpricing.discount.domain.roundtrip

import jrpricing.discount.domain.route.Route
import jrpricing.discount.domain.shared.TripType

/**
 * 往復割引計算サービス
 */
class RoundTripDiscountCalcService {
    fun calc(beforeRoundTripDiscountedFare: BeforeRoundTripDiscountedFare, route: Route, tripType: TripType): RoundTripDiscountedFare {
        if (!RoundTripAppliedPolicy.can(route, tripType))
            return RoundTripDiscountedFare(beforeRoundTripDiscountedFare.amount)

        return RoundTripDiscountedFare(beforeRoundTripDiscountedFare.amount)
    }
}