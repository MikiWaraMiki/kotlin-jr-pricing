package jrpricing.discount.domain.roundtrip

import jrpricing.discount.domain.discountrate.DiscountRate
import jrpricing.discount.domain.route.Route
import jrpricing.discount.domain.shared.Amount
import jrpricing.discount.domain.shared.TripType

/**
 * 往復割引計算サービス
 */
class RoundTripDiscountCalcService {
    fun calc(beforeRoundTripDiscountedFare: BeforeRoundTripDiscountedFare, route: Route, tripType: TripType): Amount {
        if (!RoundTripAppliedPolicy.can(route, tripType))
            return beforeRoundTripDiscountedFare.amount

        val discountRate = DiscountRate.TEN_PERCENT

        val roundTripDiscountedFare = RoundTripDiscountedFare.of(beforeRoundTripDiscountedFare, discountRate.percent)

        return roundTripDiscountedFare.amount
    }
}