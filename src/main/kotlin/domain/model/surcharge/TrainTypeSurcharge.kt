package domain.model.surcharge

import domain.model.route.Route
import domain.model.shared.Price
import domain.model.surcharge.additionalcharge.NozomiAdditionalChargeTable
import domain.model.train.TrainType

/**
 * 電車種別特急料金
 */
class TrainTypeSurcharge private constructor(
    private val baseSurcharge: Surcharge,
    private val route: Route
) {
    private val nozomiAdditionalChargeTable = NozomiAdditionalChargeTable()

    private fun applyedSurcharge(): Surcharge {
        val additionalPrice = nozomiAdditionalChargeTable.price(route)

        val addedPrice = Price(baseSurcharge.price(false).value + additionalPrice.value)

        return Surcharge(addedPrice)
    }

    companion object {
        fun calc(surcharge: Surcharge, route: Route, trainType: TrainType): Surcharge {
            if (!trainType.isNozomi()) return surcharge

            return TrainTypeSurcharge(surcharge, route).applyedSurcharge()
        }
    }
}