package domain.model.discount.largegroup

import domain.model.discount.rule.DiscountRule
import domain.model.shared.Passengers

/**
 * 特別団体割引
 */
class LargeGroupDiscountRule(
    private val passengers: Passengers
): DiscountRule {

    override fun can(): Boolean {
        return passengers.totalPassengers() >= APPLY_MIN_PASSENGERS
    }

    companion object {
        private val APPLY_MIN_PASSENGERS = 31
    }
}