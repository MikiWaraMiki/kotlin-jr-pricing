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
class LargeGroupDiscount(
    private val fare: Fare,
    private val surcharge: Surcharge,
    private val passengers: Passengers
    ): Discount {
    override val discountName = DiscountName("団体割引（乗員人数が31人以上の場合に適用）")

    private val freePassengerCount = FreePassengerCount(passengers)

    fun afterDiscountedPrice(): Price {
        val discountPriceResult = mutableListOf<Price>()

        if ( (passengers.adults - freePassengerCount.adultDiscountNumber()) >= 1) {
            discountPriceResult.add(adultDiscountPrice())
        }
        
        if ( (passengers.childs - freePassengerCount.childDiscountNumber()) >= 1) {
            discountPriceResult.add(childDiscountPrice())
        }
        
        val result = discountPriceResult.fold(0) { sum, price -> sum + price.value }

        return Price(result)
    }

    private fun adultDiscountPrice(): Price {
        return discountedPrice(false, passengers.adults - freePassengerCount.adultDiscountNumber())
    }

    private fun childDiscountPrice(): Price {
        return discountedPrice(true, passengers.childs - freePassengerCount.childDiscountNumber())
    }


    private fun discountedPrice(isChild: Boolean, passengerCount: Int): Price {
        val fareTotal = Price(
            fare.price(isChild).value * passengerCount
        )

        val surchargeTotal = Price(
            surcharge.price(isChild).value * passengerCount
        )

        return Price(fareTotal.value + surchargeTotal.value)
    }


    companion object {
        private val DISCOUNT_PER = 50
    }
}