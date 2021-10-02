package domain.model.discount

import domain.model.discount.rate.DiscountRate
import domain.model.shared.Price

/**
 * 割引額クラス
 */
class Discount(
    val price: Price
) {

    companion object {
        fun to(rate: DiscountRate, basePrice: Price): Discount {
            val discountPrice = basePrice.value.div(rate.value)

            val discountedPrice = basePrice.value - discountPrice

            return Discount(
                Price.to(discountedPrice)
            )
        }
    }
}