package jrpricing.fare.usecase.fare

import jrpricing.fare.domain.catalog.CatalogBasicFareRepository
import jrpricing.fare.domain.discount.DiscountRepository
import jrpricing.fare.domain.fare.Fare
import jrpricing.fare.domain.shared.Amount
import jrpricing.fare.domain.shared.TripRoute
import jrpricing.fare.domain.shared.TripType
import jrpricing.fare.usecase.fare.exception.AssertionFailException
import jrpricing.fare.usecase.fare.exception.ErrorCode
import org.springframework.stereotype.Service
import java.lang.Exception

/**
 * 運賃検索ユースケース
 */
@Service
class FindFareByStationIDUsecase(
    private val catalogBasicFareRepository: CatalogBasicFareRepository,
    private val discountRepository: DiscountRepository
) {
    fun execute(tripRoute: TripRoute, tripType: TripType): Fare {
        val basicFare = catalogBasicFareRepository.findBasicFare(tripRoute)
            ?: throw AssertionFailException("指定された経路は登録されていません", ErrorCode.NOTFOUND_ASSERTION)

        val discountedResultAmount = discountRepository.calc(basicFare, tripRoute, tripType)
            ?: throw Exception("割引金額の算出に失敗しました")

        return Fare.of(discountedResultAmount)
    }
}