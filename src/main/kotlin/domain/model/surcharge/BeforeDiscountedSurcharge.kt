package domain.model.surcharge

import domain.model.shared.Passengers
import domain.model.shared.Price

/**
 * 割引適用前特急料金
 */
class BeforeDiscountedSurcharge private constructor(
    val amount: Price
) {

    companion object {
        fun from(surcharge: Surcharge, passengers: Passengers): BeforeDiscountedSurcharge {
            val adultTotal = surcharge.price(false).value * passengers.adults
            val childTotal = surcharge.price(true).value * passengers.childs

            val amount = Price(adultTotal + childTotal)

            return BeforeDiscountedSurcharge(amount)
        }
    }
}