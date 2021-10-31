package domain.model.discount.group

import domain.model.discount.rate.DiscountRate
import domain.model.discount.rule.DiscountRule
import domain.model.shared.Passengers
import domain.model.ticket.DepartureDate

/**
 * 8人以上の団体割引適用ルール
 */
class GroupDiscountRule(
    private val passengers: Passengers,
): DiscountRule {
    override fun can(): Boolean {
        if (passengers.totalPassengers() < APPLY_MIN_PASSENGERS)
            return false

        if (passengers.totalPassengers() > APPLY_MAX_PASSENGERS)
            return false

        return true
    }

    companion object {
        private val APPLY_MIN_PASSENGERS = 8
        private val APPLY_MAX_PASSENGERS = 30
    }

}