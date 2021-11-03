package domain.model.discount

import domain.model.fare.Fare
import domain.model.shared.Price
import domain.model.surcharge.Surcharge

/**
 * 割引インターフェース
 */
interface Discount {
    val discountName: DiscountName
    fun afterDiscountedPrice(): Price
}

interface FareDiscount : Discount {
    val fare: Fare
    fun afterDiscountFare(): Fare
}

interface SurchargeDiscount: Discount {
    fun afterDiscountSurcharge(): Surcharge
}