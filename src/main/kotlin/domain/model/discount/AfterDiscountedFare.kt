package domain.model.discount

import domain.model.fare.Fare
import domain.model.shared.Price

/**
 * 割引後運賃
 */
class AfterDiscountedFare private constructor(
    val fare: Fare
) {
    companion object {
        fun fromPrice(price: Price): AfterDiscountedFare {
            return AfterDiscountedFare(Fare(price))
        }
    }
}