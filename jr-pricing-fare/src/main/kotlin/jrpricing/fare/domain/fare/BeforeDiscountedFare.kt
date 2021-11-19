package jrpricing.fare.domain.fare

import jrpricing.fare.domain.shared.Amount

/**
 * 割引適用前運賃
 */
class BeforeDiscountedFare private constructor(
    val amount: Amount
) {
    companion object {
        fun of(amount: Amount): BeforeDiscountedFare {
            return BeforeDiscountedFare(amount)
        }
    }
}