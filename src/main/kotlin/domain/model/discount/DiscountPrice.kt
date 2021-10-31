package domain.model.discount

import domain.model.shared.Price

interface DiscountPrice {
    fun afterDiscountedPrice(): Price
}