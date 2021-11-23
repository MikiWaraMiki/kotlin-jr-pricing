package jrpricing.fare.domain.fare

import jrpricing.fare.domain.shared.Amount

/**
 * 運賃合計金額
 */
class Fare private constructor(
    val amount: Amount
){
    companion object {
        fun of(amount: Amount): Fare {
            return Fare(amount)
        }
    }
}