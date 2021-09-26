package domain.model.additionalcharge

import domain.model.additionalcharge.traintype.NozomiAdditionalChargeTable
import domain.model.shared.Price
import domain.model.shared.Route
import domain.model.train.TrainType

/**
 * 追加料金計算サービス
 */
class AdditionalChargeCalcService {
    private val nozomiAdditionalChargeTable = NozomiAdditionalChargeTable()

    fun isRequiredAdditionalCharge(trainType: TrainType): Boolean {
        return trainType == TrainType.NOZOMI
    }

    fun calcAdditionalCharge(route: Route): Price {
        return nozomiAdditionalChargeTable.price(route)
    }
}