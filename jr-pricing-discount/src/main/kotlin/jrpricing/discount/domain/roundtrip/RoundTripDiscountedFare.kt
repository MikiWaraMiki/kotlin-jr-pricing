package jrpricing.discount.domain.roundtrip

import jrpricing.discount.domain.discountrate.Percent
import jrpricing.discount.domain.shared.Amount

/**
 * 往復割引適用後運賃
 */
class RoundTripDiscountedFare private constructor(
    val amount: Amount,
) {
    companion object {
        fun of(
            beforeRoundTripDiscountedFare: BeforeRoundTripDiscountedFare,
            discountPercent: Percent
        ): RoundTripDiscountedFare {
            val result = beforeRoundTripDiscountedFare.amount.percentOf(discountPercent.restOfHundred())

            return RoundTripDiscountedFare(result)
        }
    }
}