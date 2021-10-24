package domain.model.discount

import domain.model.discount.rate.DiscountRate
import domain.model.shared.Price
import lib.domainsupport.valueobject.ValueObject
import java.lang.Math.floor
import java.math.BigDecimal

/**
 * 割引額クラス
 */
class Discount(
    val basePrice: Price, //  割引前金額
    val discount: Int // 割引後金額
) {

    fun afterDiscountedPrice(): Price {
        val result = basePrice.value - discount
        return Price.of(result)
    }

    fun discountPrice(): Price {
        return Price.of(basePrice.value - afterDiscountedPrice().value)
    }

    companion object {
        fun of(basePrice: Price, discountRate: DiscountRate): Discount {
           return Discount(
               basePrice,
               floor(basePrice.value * (discountRate.value / 100)).toInt()
           )
        }
    }
}