package domain.model.discount.largegroup

import domain.model.discount.DiscountName
import domain.model.discount.Discount
import domain.model.fare.Fare
import domain.model.shared.Passengers
import domain.model.shared.Price
import domain.model.surcharge.Surcharge

/**
 * 特別団体割引クラス
 */
class LargeGroupDiscount(private val fare: Fare, private val surcharge: Surcharge, passengers: Passengers): Discount {
    override val discountName = DiscountName("団体割引（乗員人数が31人以上の場合に適用）")

    private val discountPassengerCount = FreePassengerCount(passengers)

    override fun afterDiscountedPrice(): Price {
        val discountPriceResult = mutableListOf<Price>()

        if (discountPassengerCount.adultDiscountNumber() >= 1) {
            discountPriceResult.add(adultDiscountPrice())
        }
        
        if (discountPassengerCount.childDiscountNumber() >= 1) {
            discountPriceResult.add(childDiscountPrice())
        }
        
        val result = discountPriceResult.fold(0) { sum, price -> sum + price.value }

        return Price(result)
    }

    private fun adultDiscountPrice(): Price {
        val adultFareDiscountTotal = Price(
            fare.price(false).value * discountPassengerCount.adultDiscountNumber()
        )
        val adultSurchargeDiscountTotal = Price(
            surcharge.price(false).value * discountPassengerCount.adultDiscountNumber()
        )

        return Price(adultFareDiscountTotal.value + adultSurchargeDiscountTotal.value)
    }

    private fun childDiscountPrice(): Price {
        val childFareDiscountTotal = Price(
            fare.price(true).value * discountPassengerCount.childDiscountNumber()
        )

        val childSurchargeDiscountTotal = Price(
            surcharge.price(true).value * discountPassengerCount.childDiscountNumber()
        )

        return Price(childFareDiscountTotal.value + childSurchargeDiscountTotal.value)
    }


    companion object {
        private val DISCOUNT_PER = 50
    }
}