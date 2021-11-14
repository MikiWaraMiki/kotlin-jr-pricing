package jrpricing.demo.first.domain.model.discount

import jrpricing.demo.first.domain.model.shared.Price

/**
 * 割引インターフェース
 */
interface Discount {
    val discountName: DiscountName

    fun afterDiscounted(): Price
}