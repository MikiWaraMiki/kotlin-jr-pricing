package domain.model.discount

import domain.model.discount.rule.DiscountRule
import domain.model.shared.Price

/**
 * 割引額計算結果クラス
 */
class DiscountResult {
    private val discountList = mutableListOf<Discount>()

    fun addDiscount(discount: Discount) {
        discountList.add(discount)
    }
}