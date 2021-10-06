package domain.model.discount

import domain.model.discount.rate.DiscountRate
import domain.model.shared.Price

/**
 * 割引額クラス
 */
class Discount(
    private val discountRate: DiscountRate, // 割引率
    private val basePrice: Price //  割引前金額
) {
    private val discountResult = calc()

    fun discountPrice(): Price {
        return Price(basePrice.value - discountResult.value)
    }

    fun result(): Price {
        return discountResult
    }

    private fun calc(): Price {
        val discountValue = basePrice.value.div(discountRate.value)
        return Price.of(basePrice.value - discountValue)
    }
}