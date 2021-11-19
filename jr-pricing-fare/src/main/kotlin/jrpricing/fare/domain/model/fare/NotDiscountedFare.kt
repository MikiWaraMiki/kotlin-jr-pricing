package jrpricing.fare.domain.model.fare

import jrpricing.fare.domain.model.shared.Amount

/**
 * 割引適用前運賃
 */
class NotDiscountedFare(
    val amount: Amount
) {
    init {

    }
}