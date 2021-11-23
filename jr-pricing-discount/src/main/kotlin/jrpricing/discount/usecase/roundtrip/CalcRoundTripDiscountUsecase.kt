package jrpricing.discount.usecase.roundtrip

import jrpricing.discount.domain.roundtrip.BeforeRoundTripDiscountedFare
import jrpricing.discount.domain.roundtrip.RoundTripDiscountCalcService
import jrpricing.discount.domain.roundtrip.RoundTripDiscountedFare
import jrpricing.discount.domain.route.RouteRepository
import jrpricing.discount.domain.shared.Amount
import jrpricing.discount.domain.shared.TripType
import jrpricing.discount.usecase.exception.AssertionFailException
import jrpricing.discount.usecase.exception.ErrorCode
import org.springframework.stereotype.Service

/**
 * 往復割引計算ユースケース
 */
@Service
class CalcRoundTripDiscountUsecase(
    private val routeRepository: RouteRepository
) {
    private val roundTripDiscountCalcService = RoundTripDiscountCalcService()

    fun execute(
        departureStationId: String,
        arrivalStationId: String,
        tripType: TripType,
        baseFare: Int
    ): Amount {
        val route = routeRepository.findByStationId(departureStationId, arrivalStationId)
            ?: throw AssertionFailException("存在しない経路です", ErrorCode.NOTFOUND_ASSERTION)

        val beforeRoundTripDiscountedFare = BeforeRoundTripDiscountedFare(Amount.of(baseFare))
        return roundTripDiscountCalcService.calc(beforeRoundTripDiscountedFare, route, tripType)
    }
}