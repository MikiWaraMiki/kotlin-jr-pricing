package domain.model.surcharge

import domain.model.shared.Price

/**
 * 特急料金計算結果
 */
class SurchargeCalcResult(
    private val beforeDiscountedSurcharge: BeforeDiscountedSurcharge,
    private val afterDiscountedSurcharge: AfterDiscountedSurcharge
) {
    fun isDiscounted(): Boolean {
        return afterDiscountedSurcharge.amount.value < beforeDiscountedSurcharge.amount.value
    }

    fun amount(): Price {
        if (isDiscounted()) return afterDiscountedSurcharge.amount

        return beforeDiscountedSurcharge.amount
    }
}