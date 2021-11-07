package jrpricing.demo.first.domain.model.surcharge

import jrpricing.demo.first.domain.model.shared.Price
import jrpricing.demo.first.domain.model.ticket.TripType

/**
 * 特急料金計算結果
 */
class SurchargeCalcResult(
    private val beforeDiscountedSurcharge: BeforeDiscountedSurcharge,
    private val afterDiscountedSurcharge: AfterDiscountedSurcharge,
    private val tripType: TripType
) {
    fun isDiscounted(): Boolean {
        return afterDiscountedSurcharge.amount.value < beforeDiscountedSurcharge.amount.value
    }

    fun amount(): Price {
        val applyAmount = if(isDiscounted()) {
            afterDiscountedSurcharge.amount
        } else {
            beforeDiscountedSurcharge.amount
        }

        if (tripType.isOneway()) return applyAmount

        return Price(applyAmount.value * 2)
    }
}