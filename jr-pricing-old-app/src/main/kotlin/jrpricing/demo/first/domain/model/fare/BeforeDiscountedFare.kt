package jrpricing.demo.first.domain.model.fare

import jrpricing.demo.first.domain.model.shared.Passengers
import jrpricing.demo.first.domain.model.shared.Price

class BeforeDiscountedFare private constructor(
    val amount: Price
) {
    companion object {
        fun from(fare: Fare, passengers: Passengers): BeforeDiscountedFare {
            val adultTotal = fare.price(false).value * passengers.adults
            val childTotal = fare.price(true).value * passengers.childs

            val amount = Price.of(
                adultTotal + childTotal
            )

            return BeforeDiscountedFare(amount)
        }
    }
}