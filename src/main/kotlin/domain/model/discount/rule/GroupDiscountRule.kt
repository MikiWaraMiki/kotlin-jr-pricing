package domain.model.discount.rule

import domain.model.discount.rate.DiscountRate
import domain.model.discount.rate.GroupDiscountRateCategory
import domain.model.discount.rule.DiscountRule
import domain.model.fare.Fare
import domain.model.purcharse.Passengers
import domain.model.ticket.DepartureDate
import lib.domainsupport.valueobject.DateRange
import java.time.LocalDate

/**
 * 8人以上の団体割引適用ルール
 */
class GroupDiscountRule(
    private val passengers: Passengers,
    private val departureDate: DepartureDate
): DiscountRule {
    private val GREETER_THEN_PASSENGER_NUM = 8

    override fun can(): Boolean {
        return passengers.totalPassengers() >= GREETER_THEN_PASSENGER_NUM
    }

    override fun rate(): DiscountRate {
        return GroupDiscountRateCategory.rateFromDate(departureDate.date)
    }
}