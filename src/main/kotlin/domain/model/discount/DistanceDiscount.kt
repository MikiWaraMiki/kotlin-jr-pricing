package domain.model.discount

import domain.model.discount.rate.DiscountRate
import domain.model.shared.Price

/**
 * 移動距離割引クラス
 */
class DistanceDiscount(
    basePrice: Price,
    discountRate: DiscountRate
) {
    private val discount: Discount

    init {
        discount = Discount.of(basePrice, discountRate)
    }
}