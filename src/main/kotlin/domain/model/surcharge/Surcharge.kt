package domain.model.surcharge

import domain.model.shared.Price

/**
 * 特急料金クラス
 */
class Surcharge(
    aPrice: Price
) {
    var price: Price = aPrice
        private set

    fun addPrice(added: Price) {
        price = Price(price.value + added.value)
    }

    fun discountPrice(discounted: Price) {
        price = Price(price.value - discounted.value)
    }
}