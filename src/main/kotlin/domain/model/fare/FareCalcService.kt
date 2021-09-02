package domain.model.fare

import domain.model.shared.Route

/**
 * 運賃計算サービス
 */
class FareCalcService {
    companion object {

        fun calcFare(route: Route): Int {
            val fareTable = FareTable()
            val fare = fareTable.fare(route)

            return fare.value
        }
    }
}