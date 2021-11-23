package jrpricing.catalog.domain.model.surcharge

import jrpricing.catalog.domain.exception.DomainException
import jrpricing.catalog.domain.exception.ErrorCode
import jrpricing.catalog.domain.model.fare.BasicFare
import jrpricing.catalog.domain.model.route.RouteId
import jrpricing.catalog.domain.model.shared.Amount

/**
 * 特急料金基本料金
 */
class BasicSurcharge private constructor(
    val routeId: RouteId,
    val amount: Amount
) {
    init {
        // TODO: 運賃と重複がある
        if (amount.value % 10 > 0) throw DomainException("特急料金は10円単位である必要があります")
    }

    fun addNozomiAdditionalCharge(nozomiAdditionalCharge: NozomiAdditionalCharge): BasicSurcharge {
        if (routeId != nozomiAdditionalCharge.routeId)
            throw DomainException("経路が一致しないためのぞみ利用時の料金を加算することができません", ErrorCode.INVALID_INPUT)

        val result = Amount(amount.value.plus(nozomiAdditionalCharge.amount.value))

        return BasicSurcharge.reConstructor(
            routeId,
            result
        )
    }

    companion object {
        fun reConstructor(routeId: RouteId, amount: Amount): BasicSurcharge {
            return BasicSurcharge(routeId, amount)
        }
    }
}