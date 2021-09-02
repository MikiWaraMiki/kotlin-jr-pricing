package domain.model.fare

import domain.model.shared.Route

/**
 * 運賃計算サービス
 */
class FaraCalcService {
    companion object {
        private val fareTable = FareTable()

        fun calcFare(route: Route): Int {
            val fare = fareTable.fare(route)

            return fare.value
        }
    }
}