package domain.model.discount

import domain.model.discount.largegroup.LargeGroupDiscountRule
import domain.model.discount.largegroup.LargeGroupFareDiscount
import domain.model.discount.largegroup.LargeGroupSurchargeDiscount
import domain.model.shared.Passengers
import domain.model.shared.Price
import domain.model.surcharge.Surcharge

/**
 * 特急料金割引計算
 */
class SurchargeDiscountCalcService(
    private val largeGroupDiscountRule: DiscountRule
) {

    fun calc(
        surcharge: Surcharge,
        passengers: Passengers
    ): Price {
        return if (largeGroupDiscountRule.can()) {
            val largeGroupSurchargeDiscount = LargeGroupSurchargeDiscount(surcharge, passengers)
            largeGroupSurchargeDiscount.afterDiscounted()
        } else {
            val adultTotal = surcharge.price(false).value * passengers.adults
            val childTotal = surcharge.price(true).value * passengers.childs

            Price(adultTotal + childTotal)
        }
    }

    companion object {
        fun withGenerateRule(passengers: Passengers): SurchargeDiscountCalcService {
            val largeGroupDiscountRule = LargeGroupDiscountRule(passengers)

            return SurchargeDiscountCalcService(largeGroupDiscountRule)
        }
    }
}