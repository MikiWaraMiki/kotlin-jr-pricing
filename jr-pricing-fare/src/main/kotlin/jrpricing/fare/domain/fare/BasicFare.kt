package jrpricing.fare.domain.fare

import jrpricing.fare.domain.shared.Amount

/**
 * 割引適用前運賃
 */
class BasicFare private constructor(
    val amount: Amount
) {
    companion object {
        fun of(amount: Amount): BasicFare {
            return BasicFare(amount)
        }
    }
}