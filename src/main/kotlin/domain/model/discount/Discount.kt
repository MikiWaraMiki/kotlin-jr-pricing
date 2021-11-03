package domain.model.discount

import domain.model.shared.Price

/**
 * 割引インターフェース
 */
interface Discount {
    val discountName: DiscountName

    fun afterDiscountedPrice(): Price
}