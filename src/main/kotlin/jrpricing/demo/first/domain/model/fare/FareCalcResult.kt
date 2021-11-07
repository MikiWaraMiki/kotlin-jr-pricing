package jrpricing.demo.first.domain.model.fare

import jrpricing.demo.first.domain.model.fare.AfterDiscountedFare
import jrpricing.demo.first.domain.model.fare.BeforeDiscountedFare
import jrpricing.demo.first.domain.model.shared.Price
import jrpricing.demo.first.domain.model.ticket.TripType

class FareCalcResult (
    private val beforeDiscountedTotalPrice: BeforeDiscountedFare,
    private val afterDiscountedTotalPrice: AfterDiscountedFare,
    private val tripType: TripType
){
    fun isDiscounted(): Boolean {
        return beforeDiscountedTotalPrice.amount.value > afterDiscountedTotalPrice.amount.value
    }

    fun amount(): Price {
        val applyPrice = if(isDiscounted()) {
            afterDiscountedTotalPrice.amount
        } else {
            beforeDiscountedTotalPrice.amount
        }

        if (tripType.isOneway()) return applyPrice

        return Price(applyPrice.value * 2)
    }
}